<div align="center">
    <img width="200" src="https://github.com/Peeter95/woermann-2022/blob/main/resources/public/images/logo.png?raw=true">
    <h1>Woermann - 2022</h1>
</div>

To get this repo with the submodules included:

    git clone --recurse-submodules -j8 git@github.com:Peeter95/woermann-2022.git

Install node modules:

    npm install

To start site development enter:

    clj -X:site.dev

To start admin development enter:

    clj -X:admin.dev

After build is ready:

Open the browser on `localhost:3000` or `localhost:3000/admin`

To compile a `jar` executable version please use this command:

    clj -X:prod

To run the `jar` use this:

    java -jar woermann.jar 3000

To connect Clojure Nrepl with Atom + Chlorine use port `5555`

To connect Shadow-CLJS Nrepl with Atom + Chlorine use build `app`
