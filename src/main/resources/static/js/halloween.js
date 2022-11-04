function saveSticker(){
  const requestDto = {
    'code' : document.getElementById("code").value,
    'userId' : 1,
    'categoryId' : 1
  };
  common.sendAjax(requestDto, 'POST', '/api/stickers', afterSaveSticker)
}

function afterSaveSticker(response){
 alert('저장에 성공하였습니다.\n'+JSON.stringify(response));
}