document.getElementById('language-select').addEventListener('change', function() {
    const lang = this.value;
    if (lang === 'ru') {
        window.location.href = 'index-ru.html';
    } else {
        window.location.href = 'index.html';
    }
});