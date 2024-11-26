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

function addGenre() {
    const genresContainer = document.getElementById('genresContainer');
    const newGenreDiv = document.createElement('div');
    newGenreDiv.innerHTML = `
        <h5 class="card-title">
            <input type="checkbox" class="genreCheckbox" checked />
            <input type="text" class="form-control d-inline-block genre-input" placeholder="Novo Gênero" style="width: auto; display: inline-block;" />
        </h5>
        <hr/>
    `;
    genresContainer.appendChild(newGenreDiv);

    const input = newGenreDiv.querySelector('.genre-input');
    input.focus();
}

function addPlatform() {
    const platformsContainer = document.getElementById('platformsContainer');
    const newPlatformDiv = document.createElement('div');
    newPlatformDiv.innerHTML = `
        <h5 class="card-title">
            <input type="checkbox" class="platformCheckbox" checked />
            <input type="text" class="form-control d-inline-block platform-input" placeholder="Nova Plataforma" style="width: auto; display: inline-block;" />
        </h5>
        <hr/>
    `;
    platformsContainer.appendChild(newPlatformDiv);

    const input = newPlatformDiv.querySelector('.platform-input');
    input.focus();
}

function addCompany() {
    const companiesContainer = document.getElementById('companiesContainer');
    const newCompanyDiv = document.createElement('div');

    newCompanyDiv.innerHTML = `
        <div id="companyContainer">
            <h5 class="card-title">
                <input type="checkbox" class="companyCheckbox" checked />
                <input type="text" class="form-control d-inline-block company-name" placeholder="Nome da Empresa" style="width: auto; display: inline-block;" />
            </h5>
            <p class="card-text">
                <strong>Desenvolvedor:</strong>
                <select class="form-control d-inline-block developer-input" style="width: auto; display: inline-block;">
                    <option value="true">Sim</option>
                    <option value="false" selected>Não</option>
                </select><br>
                <strong>Publicador:</strong>
                <select class="form-control d-inline-block publisher-input" style="width: auto; display: inline-block;">
                    <option value="true">Sim</option>
                    <option value="false" selected>Não</option>
                </select><br> 
                <strong>País:</strong>
                <input type="text" class="form-control d-inline-block country-name" placeholder="Nome do País" style="width: auto; display: inline-block;" />
                (<input type="text" class="form-control d-inline-block country-code" placeholder="Código do País" style="width: 50px; display: inline-block;" />)
            </p>
            <hr/>
        </div>
    `;

    companiesContainer.appendChild(newCompanyDiv);

    const input = newCompanyDiv.querySelector('.company-input');
    input.focus();
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
        h1.innerText = input.value || 'Nome do Jogo';
        input.style.display = 'none';
        h1.style.display = 'inline';
        icon.style.display = 'inline';
    });
}

function getSelections() {
    const genres = Array.from(document.querySelectorAll('.genreCheckbox:checked'))
        .map(checkbox => capitalizeEachWord(checkbox.nextElementSibling.value.trim()));

    const platforms = Array.from(document.querySelectorAll('.platformCheckbox:checked'))
        .map(checkbox => capitalizeEachWord(checkbox.nextElementSibling.value.trim()));

    const companies = Array.from(document.querySelectorAll('.companyCheckbox:checked'))
        .map(checkbox => {
            const studioDiv = checkbox.closest('#companyContainer');

            const companyName = studioDiv.querySelector('.company-name')?.value || '';
            const developer = studioDiv.querySelector('.developer')?.textContent.trim() === 'Sim';
            const publisher = studioDiv.querySelector('.publisher')?.textContent.trim() === 'Sim';
            const countryName = studioDiv.querySelector('.country-name')?.textContent || studioDiv.querySelector('.country-name')?.value;
            const countryCode = studioDiv.querySelector('.country-code')?.textContent || studioDiv.querySelector('.country-code')?.value;

            return {
                companyName: capitalizeEachWord(companyName), 
                developer,
                publisher,
                countryInfo: {
                    name: {
                        common: capitalizeEachWord(countryName)
                    },
                    cca2: countryCode
                }
            };
    });
    
    const metacritic = parseInt(document.getElementById('metacriticInput').value) || 0;
    const yearOfRelease = parseInt(document.getElementById('yearOfReleaseInput').value) || 0;

    const gameNameElement = document.getElementById('gameNameInput') || document.querySelector('h1');
    const name = gameNameElement.value || gameNameElement.textContent.trim();

    return {
        name: name,
        yearOfRelease: yearOfRelease,
        metacritic: metacritic,
        genres: genres,
        consoles: platforms,
        studios: companies
    };
}

async function sendGameData(gameData) {
    console.log("gameData:", gameData);
    try {
        const response = await fetch('http://localhost:8080/utils/admin/igdb/creategame', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(gameData)
        });

        if (!response.ok) {
            const errorData = await response.text();
            throw new Error(`Erro: ${response.status}` + ` - ${errorData}` || 'Erro ao salvar o jogo');
        }

        const responseData = await response.json();
        //console.log("response data from API:", JSON.stringify(responseData, null, 2));
        alert(`Jogo ${responseData.name} criado com sucesso!`);
    } catch (error) {
        console.error(error);
        alert('Erro ao criar o jogo. Verifique os detalhes no console.');
    }
}

const handleCreateGame = async () => {
    const gameData = getSelections();
    await sendGameData(gameData);
}

function capitalizeEachWord(str) {
    if (!str) return str;
    return str
        .split(' ')        
        .map(word =>         
            word.charAt(0).toUpperCase() + word.slice(1).toLowerCase() 
        )
        .join(' ');          
}