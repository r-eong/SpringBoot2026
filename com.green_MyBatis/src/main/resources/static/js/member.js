/* 회원가입 유효성 검사 규칙 */

function signupForm(){
	console.log("회원가입 폼");
	/* DOM 으로 form 연결 */
	let form = document.signup_form;
	
	/* <input type="text" name="id" class="content" placeholder="아이디를 입력하세요" /> */
	if(form.id.value === ""){
		alert("새로운 id 입력");
		/* 커서를 id 로 지정 */
		form.id.focus();
	}else if(form.pw.value === ""){
		alert("새로운 pw 입력");
		/* 커서를 pw 로 지정 */
		form.pw.focus();
	}else if(form.mail.value === ""){
		alert("새로운 mail 입력");
		/* 커서를 mail 로 지정 */
		form.mail.focus();
	}else if(form.phone.value === ""){
		alert("새로운 phone 입력");
		/* 커서를 phone 로 지정 */
		form.phone.focus();
	}else{
		/* 전송 */
		form.submit();
	}
}

/* 회원이 로그인 되는 상태일때만 게시판 게시글 작성 가능. 
아니면 로그인 후 이용 가능 메시지 출력 */
let write = document.getElementById("writeBtn");

write.addEventListener("click", function(){
	const isLogin = this.dataset.login;
	
	/* 로그인 된 상태 - 게시글 작성 가능 : /board/write */
	if(isLogin == "true"){
		location.href = "/board/write";
		
	/* 비로그인 상태 - 게시글 작성 불가능 - alert - 로그인 페이지로 이동 : /member/login */
	}else{
		alert("로그인 후 이용 가능합니다.");
		location.href = "/member/login";
	}
})