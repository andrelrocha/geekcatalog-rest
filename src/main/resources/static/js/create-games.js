function clearSelections() {
    document.querySelectorAll('.genreCheckbox').forEach(checkbox => {
        checkbox.checked = false;
    });
    document.querySelectorAll('.platformCheckbox').forEach(checkbox => {
        checkbox.checked = false;
    });
    document.querySelectorAll('.companyCheckbox').forEach(checkbox => {
        checkbox.checked = false;
    });
    document.getElementById('metacriticInput').value = '';
}

function getSelections() {
    const genres = Array.from(document.querySelectorAll('.genreCheckbox:checked'))
                        .map(checkbox => checkbox.value);
    const platforms = Array.from(document.querySelectorAll('.platformCheckbox:checked'))
                           .map(checkbox => checkbox.value);
    const companies = Array.from(document.querySelectorAll('.companyCheckbox:checked'))
                           .map(checkbox => checkbox.value);
    const metacritic = document.getElementById('metacriticInput').value;

    let message = "Gêneros selecionados: " + genres.join(", ") + "\n";
    message += "Plataformas selecionadas: " + platforms.join(", ") + "\n";
    message += "Estúdios selecionados: " + companies.join(", ") + "\n";
    message += "Pontuação Metacritic: " + metacritic;

    alert(message);
}

function addGenre() {
    const genresContainer = document.getElementById('genresContainer');
    const newGenreDiv = document.createElement('div');
    newGenreDiv.innerHTML = `
        <h5 class="card-title">
            <input type="checkbox" class="genreCheckbox" />
            <input type="text" class="form-control d-inline-block genre-input" placeholder="Novo Gênero" style="width: auto; display: inline-block;" />
        </h5>
        <hr/>
    `;
    genresContainer.appendChild(newGenreDiv);
}
