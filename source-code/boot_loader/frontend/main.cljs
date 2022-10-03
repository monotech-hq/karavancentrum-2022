
(ns boot-loader.frontend.main
    (:require ; *
              [boot-loader.frontend.app]
              [boot-loader.frontend.project]
              [boot-loader.frontend.site]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- site-structure
  [ui-structure]
  [site-wrapper/view ui-structure])

(defn- app-structure
  [ui-structure]
  [ui-structure])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-site!  [] (boot-loader/start-app!  #'site-structure))
(defn render-site! [] (boot-loader/render-app! #'site-structure))

(defn start-app!  [] (boot-loader/start-app!  #'app-structure))
(defn render-app! [] (boot-loader/render-app! #'app-structure))
