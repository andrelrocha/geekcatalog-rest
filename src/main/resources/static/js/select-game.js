function navigateToGame() {
    const gameName = document.getElementById('gameNameInput').value;
    if (gameName) {
        document.getElementById('container').style.display = 'none';
        document.getElementById('spinner').style.display = 'block';
        document.getElementById('loadingText').style.display = 'block';

        setTimeout(function() {
            window.location.href = `creategame/${encodeURIComponent(gameName)}`;
        }, 1000);
    } else {
        alert('Por favor, digite o nome do jogo!');
    }
}