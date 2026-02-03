/* 회원가입 유효성 검사 규칙 */
/* 회원가입 페이지의 input 이 비어있을 때 뜨는 경고?창 같은거임!! */
//function signupForm(){
//	console.log("회원가입 폼");
//	
//	/* DOM 으로 form 연결*/
//	let form = document.signup_form;
//	
//	if(form.id.value === ""){
//		alert("아이디를 입력하세요");
//		form.id.focus();
//	}else if(form.pw.value === ""){
//		alert("비밀번호를 입력하세요")
//		form.pw.focus();
//	}else if(form.mail.value === ""){
//		alert("비밀번호를 입력하세요")
//		form.mail.focus();
//	}else if(form.phone.value === ""){
//		alert("비밀번호를 입력하세요")
//		form.phone.focus();
//	}else {
//		form.submit();
//	}
//}

/* 회원가입 - 로그인 된 상태에서만 게시판 게시글 작성 가능
	비로그인시 로그인 후 이용 가능 메시지 출력 */
let write = document.getElementById("writeBtn");

write.addEventListener("click", function(){
	const isLogin = this.dataset.login;
	
	/* 로그인상태 - 게시글 작성 가능 */
	if(isLogin == "true"){
		location.href = "/board/write";
	
	/* 비로그인상태 - 게시글 작성 불가 */
	}else{
		alert("로그인 후 이용 가능합니다");
		location.href = "/member/login";
	}
})