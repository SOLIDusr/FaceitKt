function OnclickOpenMenu() {
  document.getElementById("header-nav").style.display = "block";
  document.getElementById("close-menu").style.display = "block";
  document.getElementById("open-menu").style.display = "none";
}

function OnclickCloseMenu() {
  document.getElementById("header-nav").style.display = "none";
  document.getElementById("close-menu").style.display = "none";
  document.getElementById("open-menu").style.display = "block";
}

function switchToRussian() {
  window.location.href = './index-ru.html';
}

function switchToEnglish() {
  window.location.href = './index.html';
}

function switchToRussianDocs() {
  window.location.href = './docs-ru.html';
}

function switchToEnglishDocs() {
  window.location.href = './docs.html';
}
// window.onscroll = function () {
//   scrollFunction();
// };
// function scrollFunction() {
//   window.scrollTo({ top: 0, behavior: "smooth" });
// }
