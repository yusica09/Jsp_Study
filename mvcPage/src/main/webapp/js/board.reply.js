$(function(){
	let rowCount = 10;
	let currentPage;
	let count;
	/* ==============================
	 * 댓글 목록
	 * ============================== */
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		//로딩 이미지 노출
		$('#loading').show();
		//서버와 통신
		$.ajax({
			url:'listReply.do',
			type:'post',
			data:{pageNum:pageNum,rowCount:rowCount,board_num:$('#board_num').val()},
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				
				if(pageNum == 1){
					//처음 호출시는 해당ID의 div의 내부 내용물으 제거
					$('#output').empty();
				}
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<h4>' + item.id + '</h4>';
					output += '<div class="sub-item">' ;
					output += '<p>' + item.re_content + '</p>';
					
					if(item.re_modifydate){
						output += '<span class="modify-date">최근 수정일 : ' + item.re_modifydate + '</span>';
					}else{
						output += '<span class="modify-date">등록일 : ' + item.re_date + '</span>';
					}
					//로그인한 회원번호와 작성자의 회원번호 일치 여부
					if(param.user_num == item.mem_num){
						//로그인한 회원번호와 작성자 회원번호 일치
						output += ' <input type="button" data-renum="'+item.re_num+
												'" value="수정" class="modify-btn">';
						output += ' <input type="button" data-renum="'+item.re_num+
												'" value="삭제" class="delete-btn">';
					}
					output += '<hr size="1" noshade width="100%">';
					output += '</div>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				
				//page button 처리
				if(currentPage >= Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
			},
			error:function(){
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
			
		});
	}
	//페이지 처리 이벤트 연결 (다음 댓글 보기 버튼 클릭시 데이터 추가)
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	
	/* ==============================
	 * 댓글 등록
	 * ============================== */
	// 댓글 등록 이벤트 연결
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim() == ''){
			alert('내용을 입력하세요!');
			$('#re_content').val('').focus();
			return false;
		}
		
		//form 이하의 태그에 입력한 데이터를 모두
		//읽어서 위치 스트링으로 변함
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success: function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 입력한 글을 포함해서 
					//첫번째 페이지의 게시글 목록을 다시 호출함
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); //end of ajax
		
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	/* ==============================
	 * 댓글 수정
	 * ============================== */
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-renum');
		//댓글 내용
		//버튼의 부모(sub-item)에서의 <p>태그 == 댓글 내용
		let content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
		//																/'문자열'/g:지정 문자열 모두, i:대소문자 무시
		//댓글 수정폼 UI
		let modifyUI = '<form id="mre_form">';
		modifyUI += '<input type="hidden" name="re_num" id="mre_num" value="'+re_num+'">';
		modifyUI += '<textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">' 
									+ content + '</textarea>';
		modifyUI += '<div id="mre_first"><span class="letter-count">300/300</span></div>';
		modifyUI += '<div id="mre_second" class="align-right">';
		modifyUI += '<input type="submit" value="수정">';
		modifyUI += '<input type="button" value="취소" class="re-reset">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면
		//숨김 sub-item 클래스로 지정한 div를 환원시키고 수정폼을 제거
		initModifyForm();
		//지금 클릭해서 수정하고자 하는 데이터는 감추기
		//수정버튼을 감싸고 있는 div
		$(this).parent().hide();
		
		//수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		$(this).parents('.item').append(modifyUI);
		
		//입력한 글자수 셋팅
		let inputLenght = $('#mre_content').val().length;
		let remain = 300 - inputLenght;
		remain += '/300';
	});
	
	
	//댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show(); //div 노출
		$('#mre_form').remove(); //수정폼 제거
	}
	
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});
	
	//댓글 수정
	$(document).on('submit','#mre_form',function(event){
		//기본 이벤트 제거
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#mre_content').val('').focus();
			return false;
		}
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'updateReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result == 'success'){
					$('#mre_form').parent()
										  .find('p')
										  .html(useBrNoHtml($('#mre_content').val()));
										  
					$('#mre_form').parent()
										  .find('.modify-date')
										  .text('최근 수정일 : 5초 미만');
					
					// 수정폼 삭제 및 초기화
					initModifyForm();
					
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글을 수정할 수 없습니다.');
				}else{
					alert('댓글 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
			
		});
		
		event.preventDefault();
		
	});
	
	/*======================
	 * HTML 태그를 허용하지 않으면서 줄바꿈
	 * ======================*/
	function useBrNoHtml(str){
		return str.replace(/</g, '&lt;')
					 .replace(/>/g, '&gt;')
					 .replace(/\r\n/g,'<br>')
					 .replace(/\r/g,'<br>')
					 .replace(/\n/g,'<br>')
	}
	
	
	/* ==============================
	 * 댓글 등록 및 수정 공통 (textarea에 내용 입력시 글자수 체크)
	 * ============================== */
	$(document).on('keyup','textarea',function(){
		//입력한 글자수 구함
		let inputLength = $(this).val().length;
		
		if(inputLength > 300){//300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300));
		}else{//300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300';
			if($(this).attr('id') == 're_content'){
				//등록 폼 글자수
				$('#re_first .letter-count').text(remain);
			}else{
				//수정 폼 글자수
				$('#mre_first .letter-count').text(remain);
			}
		}
		
	});
	/* ==============================
	 * 댓글 삭제
	 * ============================== */				
	$(document).on('click','.delete-btn',function(){
		//댓글번호
		let re_num = $(this).attr('data-renum');
		//서버와 통신
		$.ajax({
			url : 'deleteReply.do',
			type : 'post',
			data : {re_num:re_num},
			dataType:'json',
			success:function(param){
				if(param.result=='logout'){
					alert('로그인해야 삭제할 수 있습니다.');
				}else if(param.result=='success'){
					alert('삭제 완료!');
					selectList(1);
				}else if(param.result=='wrongAccess'){
					alert('타인의 글을 삭제할 수 없습니다.');		
				}else{
					alert('오류 발생');
				}
				
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
			
			
		});
		
	});
	/* ==============================
	 * 초기 데이터(목록) 호출
	 * ============================== */
	selectList(1);
});








