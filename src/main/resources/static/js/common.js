const digits = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_';
const binArray =  ['000000','000001','000010','000011','000100','000101','000110','000111',
  '001000','001001','001010','001011','001100','001101','001110','001111',
  '010000','010001','010010','010011','010100','010101','010110','010111',
  '011000','011001','011010','011011','011100','011101','011110','011111',
  '100000','100001','100010','100011','100100','100101','100110','100111',
  '101000','101001','101010','101011','101100','101101','101110','101111',
  '110000','110001','110010','110011','110100','110101','110110','110111',
  '111000','111001','111010','111011','111100','111101','111110','111111'];

addReverseBtnEvent();
addShareLinkEvent();

function addReverseBtnEvent(){
  const reverseBtn = document.getElementById('reverseBtn');
  if(reverseBtn){
    reverseBtn.addEventListener('click', function(event){
      let image = document.querySelectorAll('.image > div');
      for (var i = 0 ; i < image.length; i++) {
        image[i].classList.toggle("selected");
      }
      createLink();
    });
  }
}

function addShareLinkEvent(){
  const shareLink = document.getElementById("shareLink");
  if(shareLink){
    shareLink.style.cursor = 'pointer';
    shareLink.addEventListener('click', function(event){
      this.select();
      document.execCommand('copy');
      this.setSelectionRange(0, this.value.length);
      alert("링크가 복사되었습니다.");
    });
  }
}

function toggleImage(){
  const code = document .getElementById("code").value;
  const image = document.querySelectorAll('.image > div');
  const decoded = decode(code);

  for(let i=0; i < image.length; i++){
    if(decoded && decoded[i] == '1'){
      image[i].classList.toggle('selected');
    }
    image[i].addEventListener('click', function(e){
      e.target.classList.toggle("selected");
      createLink();
    });
  }
  createLink();
}


function decode(code) {
  if(code) {
    let decodedCode = '';
    for(let i = 0; i < code.length; i++) {
      decodedCode += binArray[digits.indexOf(code[i])];
    }
    return decodedCode;
  }
  return code;
}

function encode(code) {
  let encodedCode = '';
  for(let i = 0; i < (parseInt(code.length/6)); i++) {
    encodedCode += digits.charAt(parseInt(code.substr(i*6,6),2));
  }
  return encodedCode;
}

function createLink() {
  const url = window.location.host + window.location.pathname;
  let image = document.querySelectorAll('.image > div');
  let codeStr = '';
  let cnt = 0;

  for (var i = 0 ; i < image.length; i++) {
    codeStr += (image[i].classList.contains('selected')?'1':'0');
    if(image[i].classList.contains('selected')) {
      cnt++;
    }
  }
  const resultCode = encode(codeStr + '00000');
  console.log(resultCode + "/ " + cnt);
  document.getElementById('cnt').innerHTML = cnt + '/' + image.length;
  document.getElementById("code").value =  resultCode;
  document.getElementById("shareLink").value = url+"?code="+resultCode;
}

const common = {
  sendAjax : function(sendData, sendMethod, sendUrl, callbackFunc){
      const httpRequest = new XMLHttpRequest();
      httpRequest.onreadystatechange = function() {
        if(httpRequest.readyState == XMLHttpRequest.DONE){
          if(httpRequest.status == 200 ){
            const result = httpRequest.response;
            if(callbackFunc != undefined && callbackFunc != null){
              callbackFunc(result);
            }
          }else{
            alert('실패');
          }
        }
      };
      const data = JSON.stringify(sendData);
      httpRequest.open(sendMethod, sendUrl, true);
      httpRequest.setRequestHeader('Content-Type','application/json');
      httpRequest.send(data);
    }
}