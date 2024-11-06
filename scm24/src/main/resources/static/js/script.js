// todo
// this is for theme change 
let currentTheme = getTheme();
changeTheme();

function changeTheme() {
    document.querySelector('html').classList.add(currentTheme);

    // to chenge theme by button...
    const changeThemeButton = document.querySelector('#them_change_button');
    changeThemeButton.addEventListener("click", (event) => {
        let oldTheme = currentTheme;

        console.log("change theme button clicked");
        if (currentTheme === "dark") {
            currentTheme = "light";

        } else {
            currentTheme = "dark";
        }

        // to change in the localstorage..
        setTheme(currentTheme);
        // remove the current theme first...
        document.querySelector("html").classList.remove(oldTheme);
        // set the new theme ...
        document.querySelector("html").classList.add(currentTheme);
        console.log(currentTheme);

        /// change the text of button 

        changeThemeButton.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";
    });

}


//set theme to localStorage

function setTheme(theme) {
    localStorage.setItem("theme", theme)
}

// get storage from localstorage..
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}