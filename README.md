<div align="center">
    <img width="200" src="https://github.com/monotech-hq/karavancentrum-2022/blob/main/resources/public/images/logo.png?raw=true">
    <h1>Karaváncentrum - 2022</h1>
</div>

To get this repo with the submodules included:

    git clone --recurse-submodules -j8 git@github.com:monotech-hq/karavancentrum-2022.git

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

    java -jar karavancentrum.jar 3000

To connect Clojure Nrepl with Atom + Chlorine use port `5555`

To connect Shadow-CLJS Nrepl with Atom + Chlorine use build `app`
