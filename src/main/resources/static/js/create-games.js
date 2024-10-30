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
                           .map(checkbox => checkbox.nextElementSibling.value);
    const companies = Array.from(document.querySelectorAll('.companyCheckbox:checked'))
                           .map(checkbox => {
                               const cardBody = checkbox.closest('.card-body');
                               const countryName = cardBody.querySelector('.country-input').value;

                               return {
                                   name: checkbox.nextElementSibling.value,
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

function addPlatform() {
    const platformsContainer = document.getElementById('platformsContainer');
    const newPlatformDiv = document.createElement('div');
    newPlatformDiv.innerHTML = `
        <h5 class="card-title">
            <input type="checkbox" class="platformCheckbox" />
            <input type="text" class="form-control d-inline-block platform-input" placeholder="Nova Plataforma" style="width: auto; display: inline-block;" />
        </h5>
        <hr/>
    `;
    platformsContainer.appendChild(newPlatformDiv);
}

function addCompany() {
    const companiesContainer = document.getElementById('companiesContainer');
    const newCompanyDiv = document.createElement('div');
    newCompanyDiv.innerHTML = `
        <h5 class="card-title">
            <input type="checkbox" class="companyCheckbox" />
            <input type="text" class="form-control d-inline-block company-input" placeholder="Nome da Empresa" style="width: auto; display: inline-block;" />
        </h5>
        <p class="card-text">
            <strong>País:</strong> <input type="text" class="form-control d-inline-block country-input" placeholder="Nome do País" style="width: auto; display: inline-block;" />
        </p>
        <hr/>
    `;
    companiesContainer.appendChild(newCompanyDiv);
}

function editGameName() {
    const titleContainer = document.getElementById('titleContainer');
    const h1 = titleContainer.querySelector('h1');
    const input = titleContainer.querySelector('#gameNameInput');
    const icon = titleContainer.querySelector('i');

    if (h1) {
        h1.style.display = 'none';
    }
    if (icon) {
        icon.style.display = 'none';
    }

    input.style.display = 'inline-block';
    input.focus();

    input.addEventListener('blur', function() {
        h1.innerText = input.value;
        input.style.display = 'none';
        h1.style.display = 'inline';
        icon.style.display = 'inline';
    });
}
