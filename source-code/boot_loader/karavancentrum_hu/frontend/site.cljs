
(ns boot-loader.karavancentrum-hu.frontend.site
    (:require ; Extra modules
              [pathom.api]
              [developer-tools.api :as developer-tools]
              [x.boot-loader.api   :as x.boot-loader]

              ; App modules
              [app.contents.frontend.api]
              [app.views.frontend.api]

              ; Site modules
              [site.karavancentrum-hu.wrapper.views :as karavancentrum-hu.wrapper.views]
             ;[site.karavancentrum-hu.frontend.api :as karavancentrum-hu]
              [site.karavancentrum-hu.pages.frontend]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- site-structure
  [ui-structure]
  [:<> [karavancentrum-hu.wrapper.views/view ui-structure]
      ;[karavancentrum-hu/wrapper ui-structure]
       [developer-tools/magic-button]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-site!  [] (x.boot-loader/start-app!  #'site-structure))
(defn render-site! [] (x.boot-loader/render-app! #'site-structure))
