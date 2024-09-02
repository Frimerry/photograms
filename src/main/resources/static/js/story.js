/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 좋아요 취소
	(4) 댓글쓰기
	(5) 댓글삭제
 */

$(document).ready(function() {
  adjustImageSize();

  // 윈도우 리사이즈 시에도 이미지 사이즈 조정
  $(window).on('resize', adjustImageSize);

  // MutationObserver를 사용하여 DOM 변화를 감지
  var observer = new MutationObserver(function(mutations) {
    mutations.forEach(function(mutation) {
      if (mutation.type === 'childList') {
        adjustImageSize();
      }
    });
  });

  // .main 요소를 관찰
  var config = { childList: true, subtree: true };
  observer.observe(document.querySelector('.main'), config);
});



// (0) 현재 로그인 사용자
let principalId = $("#principalId").val();

// (1) 스토리 로드하기
let page = 0;

function storyLoad() {
	
	$.ajax({
		url:`/api/image?page=${page}`,
		dataType:"json"
		
	}).done(res=>{
		
		if(res.data.totalElements!= 0){
			res.data.content.forEach((image)=>{
				let storyItem = getStoryItem(image);
				$("#storyList").append(storyItem);
			});
		}
		else {
			const div = $('.no-content');
            const span = $('<span>').text('표시할 스토리가 없습니다.');
            div.append(span);
		}
		
	}).fail(error=>{
		console.log(error);
	});

}

storyLoad();

function getStoryItem(image) {
	
	let item = `<div class="story-list__item">
		<div class="sl__item__header">
			<div>
				<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
					onerror="this.src='/images/person.png'" />
			</div>
			<div>${image.user.name}</div>
		</div>
	
		<div class="sl__item__img">
			<img src="/upload/${image.postImageUrl}" />
		</div>
	
		<div class="sl__item__contents">
			<div class="sl__item__contents__icon">
				<button>`;
				
				if(image.likeState){
					// 좋아요 상태
					item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
				}
				else{
					// 기본 상태
					item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
				}
			
			item +=`
				</button>
			</div>
	
			<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>
	
			<div class="sl__item__contents__content">
				<p>${image.caption}</p>
			</div>
	
			<div id="storyCommentList-${image.id}">`;
			
			image.comments.forEach((comment)=>{
				item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
							<p>
								<b>${comment.user.name} :</b> ${comment.content}.
							</p>`;
							
							if(principalId == comment.user.id){
								item+= `<button onclick="deleteComment(${comment.id});">
											<i class="fas fa-times"></i>
										</button>`;
							}
				item +=	`</div>`;
			});
	
			item += `
			</div>
	
			<div class="sl__item__input">
				<input type="text" placeholder="댓글을 작성해주세요." id="storyCommentInput-${image.id}" />
				<button type="button" onClick="writeComment(${image.id})">게시</button>
			</div>
	
		</div>
	</div>`;
	
	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	
	let checkPos = $(window).scrollTop() - ($(document).height() - $(window).height())
	
	if (checkPos < 10 && checkPos > -10) {
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 좋아요 취소
function toggleLike(imageId) {
	
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	
	if (likeIcon.hasClass("far")) {
		// 기본 상태, 좋아요 실행
		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) + 1;
			
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
			
		}).fail(error=>{
			console.log("Like Error", error);
			alert("일시적인 오류로 좋아요 실패. 네트워크 상태를 확인해주세요.");
		});
		
	} else {
		// 좋아요 상태, 좋아요 취소 실행
		$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/unlikes`,
			dataType:"json"
			
		}).done(res=>{
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr) - 1;
			
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
			
		}).fail(error=>{
			console.log("Unlike Error", error);
			alert("일시적인 오류로 좋아요 취소 실패. 네트워크 상태를 확인해주세요.");
		});
		
		
	}
}

// (4) 댓글쓰기
function writeComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글 내용을 작성해주세요!");
		return;
	}
	
	$.ajax({
		type:"post",
		url:"/api/comment",
		data: JSON.stringify(data),
		contentType:"application/json; charset-utf-8",
		dataType:"json"
		
	}).done(res=>{
		console.log("댓글작성 성공", res);
		
		let comment = res.data;
		
		let content = `
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
		    <p>
		      <b>${comment.user.username} :</b>
		      ${comment.content}
		    </p>
		    <button onclick="deleteComment(${comment.id});"><i class="fas fa-times"></i></button>
		  </div>
		`;
		commentList.prepend(content);
		
	}).fail(error=>{
		alert(error.responseJSON.data.content);
		console.log("댓글 작성 실패", error);
	});

	
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
	}).done(res=>{
		console.log(res)
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		console.log(error)
	});

}

// (6) 이미지 크기 조정
function adjustImageSize() {
  $('.main .container .story-list .story-list__item .sl__item__img img').each(function() {
    var $this = $(this);
    var img = this;

    // 이미 로드된 이미지일 경우
    if (img.complete) {
      resizeImage($this, img);
    } else {
      // 이미지가 로드될 때까지 기다림
      $this.on('load', function() {
        resizeImage($this, img);
      });
    }
  });
}

function resizeImage($this, img) {
  var imgWidth = img.naturalWidth;
  var imgHeight = img.naturalHeight;

  console.log('가로:' + imgWidth);
  console.log('세로:' + imgHeight);

  if (imgWidth > imgHeight) {
    $this.css({
      width: '100%',
      height: 'auto'
    });
  } else {
    $this.css({
      width: 'auto',
      height: '100%'
    });
  }
}