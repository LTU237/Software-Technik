 window.onload = function () {


        document.getElementById("loginBtn").addEventListener("click", login);
        document.getElementById("logoutBtn").addEventListener("click", logout);
        document.getElementById("registerBtn").addEventListener("click", createInvestor);
        document.getElementById("deleteBtn").addEventListener("click", deleteInvestor);
        document.getElementById("deletePortfolioBtn").addEventListener("click", deletePortfolio);
        document.getElementById("newPortfolioBtn").addEventListener("click", createPortfolio);
     document.getElementById("registerBrokBtn").addEventListener("click", createBroker);
     document.getElementById("loginBrokBtn").addEventListener("click", loginBroker);
     document.getElementById("logoutBrokBtn").addEventListener("click", logoutBroker);
     document.getElementById("deleteBrokBtn").addEventListener("click", deleteBroker);
    document.getElementById("newAssetBtn").addEventListener("click", newAsset);
     //document.getElementById("assetdeleteBtn").addEventListener("click", assetdelete);

 }

//Goblale Variablen
    let globalId = null;
    let globalBenutzername = null;
    let globalAccessToken = null;
    let globalBrokerId = null;
    let globalCompany=null;
    let globalPortfolioId= null;


    async function createInvestor() {
        let firstname = document.getElementById("regVorname").value;
        let lastname = document.getElementById("regNachname").value;
        let username = document.getElementById("regBenutzername").value;
        let password = document.getElementById("regPassword").value;

        let investor = {
            "firstname": firstname,
            "lastname": lastname,
            "username": username,
            "password": password
        }

        try {
            const response = await fetch("http://localhost:8080/investors",
                {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(investor),
                });

            const result = await response.json();
            if (!response.ok) {
                showStatusError(result);
                throw new Error("Error: createInvestor " + result.status);
            }

            // Setzte globale Variablen
            globalId = result.id;
            globalBenutzername = result.benutzername;
            globalAccessToken = result.credential;

            showLoginData();
            console.log(result);
            clearAll();
        } catch (error) {
            console.error("Error:", error);
        }
    }

 async function createBroker() {
     let name = document.getElementById("regBrokName").value;
     let company = document.getElementById("regCompany").value;
     let password = document.getElementById("regBrokPassword").value;

     let broker = {
         "username": name,
         "company": company,
         "password": password
     }

     try {
         const response = await fetch("http://localhost:8080/brokers",
             {
                 method: 'POST',
                 headers: {
                     'Accept': 'application/json',
                     'Content-Type': 'application/json'
                 },
                 body: JSON.stringify(broker),
             });

         const result = await response.json();
         if (!response.ok) {
             showStatusError(result);
             throw new Error("Error: createBroker " + result.status);
         }

         // Setzte globale Variablen
         globalId = result.id;
         globalBenutzername = result.username;
         globalCompany= result.company;
         globalAccessToken = result.credential;

         console.log(result);
        // clearAll();
     } catch (error) {
         console.error("Error:", error);
     }
 }

 async function login() {
        let username = document.getElementById("loginBenutzername").value;
        let password = document.getElementById("loginPassword").value;
        console.log('Benutzername:', username);
        let loginData = {
            "username": username,
            "password": password
        };

        try {
            const response = await fetch("http://localhost:8080/accessInvestor",
                {
                    method: 'post',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(loginData),
                });

            const result = await response.json();

            // Setzte globale Variablen
            globalId = result.id;
            globalBenutzername = result.benutzername;
            globalAccessToken = result.credential;

            showLoginData();
            console.log(result);
            await getAllePortfolio();
            await getAlleBroker();
            clearAll();

        } catch (error) {
            console.error("Error:", error);
        }
    }
 async function loginBroker() {
     let username = document.getElementById("loginBrokname").value;
     let password = document.getElementById("loginBrokPassword").value;
     console.log('username:', username);
     let loginBrokerData = {
         "username": username,
         "password": password
     };

     try {
         const response = await fetch("http://localhost:8080/accessbroker",
             {
                 method: 'post',
                 headers: {
                     'Accept': 'application/json',
                     'Content-Type': 'application/json'
                 },
                 body: JSON.stringify(loginBrokerData),
             });

         const resultBroker = await response.json();

         // Setzte globale Variablen
         globalId = resultBroker.id;
         globalBenutzername = resultBroker.benutzername;
         globalAccessToken = resultBroker.credential;

         showLoginData();
         await getAlleAssets();
         console.log(resultBroker);

         //clearAll();

     } catch (error) {
         console.error("Error:", error);
     }
 }

    async function logout() {
        try {
            const response = await fetch("http://localhost:8080/accessInvestor",
                {
                    method: 'DELETE',
                    headers: {
                        'Accept': 'application/json',
                        'accessToken': globalAccessToken.accessToken,
                    },
                });

            const result = await response.json();

            // Setzte globale Variablen
            globalId = null;
            globalBenutzername = null;
            globalAccessToken = null;


            showLoginData();
            console.log(result);
           // clearAll();

        } catch (error) {
            console.error("Error:", error);
        }
    }
 async function logoutBroker() {
     try {
         const response = await fetch("http://localhost:8080/accessbroker",
             {
                 method: 'DELETE',
                 headers: {
                     'Accept': 'application/json',
                     'accessToken': globalAccessToken.accessToken,
                 },
             });

         const result = await response.json();

         // Setzte globale Variablen
         globalId = null;
         globalBenutzername = null;
         globalAccessToken = null;

        // clearAll();
        // showLoginData();
         console.log(result);

     } catch (error) {
         console.error("Error:", error);
     }
 }


    async function deleteInvestor() {
        try {
            const response = await fetch("http://localhost:8080/investors/" + globalId,
                {
                    method: 'delete',
                    headers: {
                        'Accept': 'application/json',
                        'accessToken': globalAccessToken.accessToken,
                    },
                });

            // Setzte globale Variablen
            globalId = null;
            globalBenutzername = null;
            globalAccessToken = null;

            //clearAll();
        } catch (error) {
            console.error("Error:", error);
        }
    }
 async function deleteBroker() {
     try {
         const response = await fetch("http://localhost:8080/brokers/" + globalId,
             {
                 method: 'delete',
                 headers: {
                     'Accept': 'application/json',
                     'accessToken': globalAccessToken.accessToken,
                 },
             });

         // Setzte globale Variablen
         globalId = null;
         globalBenutzername = null;
         globalCompany = null;
         globalAccessToken = null;

     } catch (error) {
         console.error("Error:", error);
     }
 }


 async function createPortfolio() {

     let investorId = document.getElementById("investorId").value;
     let brokerId = document.getElementById("brokerId").value;

     let portfolio = {
         "investorId": investorId,
         "brokerId": brokerId,
         "accessToken": globalAccessToken
     }

     let portfolioId;
     try {
         const response = await fetch("http://localhost:8080/investors/portfolio",
             {
                 method: 'POST',
                 headers: {
                     'Accept': 'application/json',
                     'Content-Type': 'application/json',
                     'accessToken': globalAccessToken.accessToken
                 },
                 body: JSON.stringify(portfolio),
             });

         const result = await response.json();
         if (!response.ok) {
             showStatusError(result);
             throw new Error("Error: createPortfolio " + result.status);
         }

         // Setzte globale Variablen
         globalId = result.investorId;
         portfolioId = result.id;
         globalBrokerId = result.brokerId;

         await getAllePortfolio();
         console.log(result);
         clearAll();
     } catch (error) {
         console.error("Error:", error);
     }
 }
 async function newAsset() {

     let kind = document.getElementById("kind").value;
     let name = document.getElementById("name").value;

     let asset = {
         "kind": kind,
         "name": name,
         "accessToken": globalAccessToken
     }

     let assetId;
     try {
         const response = await fetch("http://localhost:8080/brokers/" + globalId + "/asset",
             {
                 method: 'POST',
                 headers: {
                     'Accept': 'application/json',
                     'Content-Type': 'application/json',
                     'accessToken': globalAccessToken.accessToken
                 },
                 body: JSON.stringify(asset),
             });

         const resultA = await response.json();
         if (!response.ok) {
             showStatusError(resultA);
             throw new Error("Error: createAsset " + resultA.status);
         }

         // Setzte globale Variablen
         assetId = resultA.id;

         await getAlleAssets();
         console.log(resultA);
         //clearAll();
     } catch (error) {
         console.error("Error:", error);
     }
 }

    async function getAllePortfolio() {
        try {
            const response = await fetch("http://localhost:8080/investors/"+ globalId+"/portfolio",
                {
                    method: 'get',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json',
                        'accessToken': globalAccessToken.accessToken,
                    },
                });

            const result = await response.json();
            showAllePortfolio(result)
            console.log(result);
        } catch (error) {
            console.error("Error:", error);
        }
    }
 async function getAlleAssets() {
     try {
         const response = await fetch("http://localhost:8080/brokers/"+ globalId+"/asset",
             {
                 method: 'get',
                 headers: {
                     'Accept': 'application/json',
                     'Content-Type': 'application/json',
                     'accessToken': globalAccessToken.accessToken,
                 },
             });

         const resultA = await response.json();
         showAlleAsset(resultA)
         console.log(resultA);
     } catch (error) {
         console.error("Error:", error);
     }
 }
 async function getAlleBroker() {
     try {
         const response = await fetch("http://localhost:8080/investors/brokers",
             {
                 method: 'get',
                 headers: {
                     'Accept': 'application/json',
                     'Content-Type': 'application/json',
                     'accessToken': globalAccessToken.accessToken,
                 },
             });

         const result = await response.json();
         showAlleBrokers(result)
         console.log(result);
     } catch (error) {
         console.error("Error:", error);
     }
 }

 async function deletePortfolio() {
        globalPortfolioId = document.getElementById("portfolioId").value;
     try {
         const response = await fetch("http://localhost:8080/investors/" + globalId + "/portfolio/" + globalPortfolioId,
             {
                 method: 'delete',
                 headers: {
                     'Accept': 'application/json',
                     'accessToken': globalAccessToken.accessToken,
                 },
             });

         // Setzte globale Variablen
         globalId = null;
         globalBenutzername = null;
         globalAccessToken = null;

     } catch (error) {
         console.error("Error:", error);
     }
 }

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    function showAllePortfolio(portfolioliste) {
        let str = "";
        portfolioliste.forEach(element => {
            str += JSON.stringify(element) + "<br/>"
        });

        document.getElementById("outAllePortfolio").innerHTML = str;
    }

 function showAlleAsset(assetliste) {
     let str = "";
     assetliste.forEach(element => {
         str += JSON.stringify(element) + "<br/>"
     });

     document.getElementById("outAlleBrokerAssets").innerHTML = str;
 }

 function showAlleBrokers(brokerliste) {
     let str = "";
     brokerliste.forEach(element => {
         str += JSON.stringify(element) + "<br/>"
     });

     document.getElementById("outAlleBroker").innerHTML = str;
 }


    function showLoginData() {
        if (globalId != null) {
            document.getElementById("outInvestorId").innerHTML = globalId;
            document.getElementById("outBrokerId").innerHTML = globalId;
       }else {
            document.getElementById("outInvestorId").innerHTML = "Not defined";
        }

        if (globalBenutzername != null) {
            document.getElementById("outBenutzername").innerHTML = globalBenutzername;
            document.getElementById("outBrokername").innerHTML = globalBenutzername;
        } else {
            document.getElementById("outBenutzername").innerHTML = "Not defined";
        }

        if (globalAccessToken != null) {
            document.getElementById("outAccessToken").innerHTML = JSON.stringify(globalAccessToken);
            document.getElementById("outBrokerAccessToken").innerHTML = JSON.stringify(globalAccessToken);
        } else {
            document.getElementById("outAccessToken").innerHTML = "Not defined";
        }
    }


    function showStatusError(result) {
        document.getElementById("error").innerHTML = "Statuscode " + result.status + " : " + result.message;
    }


    function clearAll() {
        document.getElementById("error").innerHTML = "";
        //document.getElementById("outAlleInvestor").innerHTML = "";

        document.getElementById("loginBenutzername").value = "";
        document.getElementById("loginPassword").value = "";

        document.getElementById("regVorname").value = "";
        document.getElementById("regNachname").value = "";
        document.getElementById("regBenutzername").value = "";
        document.getElementById("regPassword").value = "";

        document.getElementById("updateVorname").value = "";
        document.getElementById("updateNachname").value = "";
        document.getElementById("updatePassword").value = "";
    }




