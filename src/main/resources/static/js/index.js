const element = document.getElementById('main');
const spinner = document.getElementById('spinner');

function hideSpinner() {
    spinner.classList.remove('show')
}

const load = async () => {
    const response = await fetch('/find-ssmb-stats');
    const html = await response.text();
    hideSpinner();
    element.innerHTML += html;
}

void load();
