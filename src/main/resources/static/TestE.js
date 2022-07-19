const callApiFirst = async () => {
    const myResponse = await fetch('http://localhost:8080/greeting');
    const myJson = await myResponse.json();
    document.getElementById("greeting").innerHTML = myJson.id + ". " + myJson.content;
}
const callApiSecond = async () => {
    const myResponse = await fetch('http://localhost:8080/greeting' + "?name=" + document.getElementById("kivagy").value);
    const myJson = await myResponse.json();
    document.getElementById("greeting").innerHTML = myJson.id + ". " + myJson.content;
}

var TempFileData;  //amikor beloginol ki kéne szedni a doksiból az adatait globális eléréssel (ami megy is miután lefut a loginUser)
//ami az oldal betöltése után történik
let FreedomFunctionOneLoad;

async function loginUser() {
    const myResponse = await fetch('http://localhost:8080/OpenReport');
    const myJson = await myResponse.json();
    document.getElementById("UserLogin").innerHTML = "Bejeletkezve : " + myJson.name;
    TempFileData = myJson;
    console.log(TempFileData)
    FreedomFunctionOneLoad = false;

    //egy ütes listába beleteszem a file-ban lévő adatot , sükség lesz rá a TableGetData funkciónál FONTOS 1X fusson le
    for (let i = 0; i < TempFileData.dates.length; i++) {
        data_list.push(TempFileData.dates[i]);
    }

}

function createCalendar(elem, year, month) { //gyönyörű naptárat készít neked

    let mon = month - 1;
    let d = new Date(year, mon);

    let table = '<table><tr><th>MO</th><th>TU</th><th>WE</th><th>TH</th><th>FR</th><th>SA</th><th>SU</th></tr><tr>';

    // spaces for the first row
    // from Monday till the first day of the month
    // * * * 1  2  3  4
    for (let i = 0; i < getDay(d); i++) {
        table += '<td></td>';
    }

    // <td> with actual dates
    while (d.getMonth() === mon) {
        table += '<td  id="' + d.getDate() + '" onclick="TableGetData(this)" ><button class="Buttoon" style="" >' + d.getDate() + '</button></td>';

        if (getDay(d) % 7 === 6) { // sunday, last day of week - newline
            table += '</tr><tr>';
        }
        d.setDate(d.getDate() + 1);
    }

    // add spaces after last days of month for the last row
    // 29 30 31 * * * *
    if (getDay(d) !== 0) {
        for (let i = getDay(d); i < 7; i++) {
            table += '<td></td>';
        }
    }
    // close the table
    table += '</tr></table>';

    elem.innerHTML = table;
}

function getDay(date) { // get day number from 0 (monday) to 6 (sunday)
    let day = date.getDay();
    if (day === 0) day = 7; // make Sunday (0) the last day
    return day - 1;
}

var data_list = [];


function TableGetData(date) {
    let dateDay = date.innerText;
    let year = document.getElementById('year').value
    let month = document.getElementById('month').value

    let tableRow = document.getElementById(dateDay);
    let tableRowButton = tableRow.children;

    let dateString = year + '-' + month + '-' + dateDay

    if (!FreedomFunctionOneLoad) {
        freedomDisplay().then();
        // it only runs later, so the "blue" condition is needed below
        window.alert("Mivel nem kérted le az eddigi szabid , betöltöttük neked :)");
        return;
    }

    if (data_list.includes(dateString) && tableRowButton[0].style.backgroundColor === "blue") {
        data_list.pop(dateString);
        tableRowButton[0].style.backgroundColor = "#F0F0F0";
        tableRowButton[0].style.color = "black";
        return;
    }

    if (!data_list.includes(dateString) && FreedomFunctionOneLoad) {
        data_list.push(dateString);
        tableRowButton[0].style.backgroundColor = "blue";
        tableRowButton[0].style.color = "white";
    }

}


function writeFile() {  //Save
    // good list :
    let Json_list = {id: 11, name: "user", dates: data_list};
    fetch('http://localhost:8080/PrintReport', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(Json_list),
    }).then(r => "")

    /*.then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
    */
}

function OpenFile() {
    fetch('http://localhost:8080/OpenReport').then(response => response.json().then(userHoliday => console.log(userHoliday)))
}

//csak az utolsó elem évére és hónapjára listázzuk ki a szabadságokat a file-bol
const freedomDisplay = async () => {
    const myResponse = await fetch('http://localhost:8080/OpenReport');
    const myJson = await myResponse.json();
    document.getElementById("UserLogin").innerHTML = "Üdvözlöm kedves : " + myJson.name;
    document.getElementById("greetings").innerHTML = "A szabadsága az alábbi naptárban látható : ";

    //itt kinyerni az év adajtát
    let year = myJson.dates[myJson.dates.length - 1].slice(0, 4); //évszám
    let month = myJson.dates[myJson.dates.length - 1].slice(5, 7); //hónap

    //atírja a felületen a számokat
    document.getElementById('year').value = year;
    document.getElementById('month').value = month;

    //ujragenerálja a táblát
    createCalendar(document.getElementById("calendar"), year, month);

    //utolsó évnek és hónapnak létrehozunk egy listát
    let date_list = [];

    //kiszedjük azokat az elemeket amik az utolsó évben vannak
    for (let i = 0; i < myJson.dates.length; i++) {
        if (myJson.dates[i].includes(year) && myJson.dates[i].slice(5, 7).includes(month)) { //csak megmondja hogytartalmazza az évet és hónapot
            date_list.push(myJson.dates[i]);
        }
    }
    //egyszerű szinezés
    for (let i = 0; i < date_list.length; i++) {
        document.getElementById(date_list[i].slice(8)).children[0].style.backgroundColor = "blue";
        document.getElementById(date_list[i].slice(8)).children[0].style.color = "white";
    }
    FreedomFunctionOneLoad = true;


}


