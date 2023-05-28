function findSummoner() {
    let serverName = document.getElementById("server-select").value;
    let summonerName =  document.getElementById("summoner-name").value;
    if (!serverName || !summonerName) {
        return;
    }
    location.href = "/summoner/" + serverName + "/" + summonerName;
}