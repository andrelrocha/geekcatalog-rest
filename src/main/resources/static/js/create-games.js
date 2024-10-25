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
    document.getElementById('yearOfReleaseInput').value = '';
}

function getSelections() {
    const genres = Array.from(document.querySelectorAll('.genreCheckbox:checked'))
                        .map(checkbox => checkbox.nextElementSibling.value);
    const platforms = Array.from(document.querySelectorAll('.platformCheckbox:checked'))
                           .map(checkbox => checkbox.value);
    const companies = Array.from(document.querySelectorAll('.companyCheckbox:checked'))
                               .map(checkbox => {
                                   const cardBody = checkbox.closest('.card-body');
                                   const countryName = cardBody.querySelector('strong:nth-of-type(3) + span').textContent;

                                   return {
                                       name: checkbox.value,
                                       country: {
                                           name: countryName,
                                       }
                                   };
                               });
    const metacritic = parseInt(document.getElementById('metacriticInput').value) || 0;
    const yearOfRelease = parseInt(document.getElementById('yearOfReleaseInput').value) || 0;

    const createGameAdminTemplateDTO = {
            name: document.querySelector('h1').textContent,
            yearOfRelease: yearOfRelease,
            metacritic: metacritic,
            genres: genres,
            consoles: platforms,
            studios: companies
    };

    console.log(JSON.stringify(createGameAdminTemplateDTO, null, 2));
    alert("Dados do jogo e estúdios foram impressos no console.");
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
