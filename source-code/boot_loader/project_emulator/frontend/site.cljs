
(ns boot-loader.project-emulator.frontend.site
    (:require [boot-loader.karavancentrum-hu.frontend.site :as karavancentrum-hu]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-site!  [] (karavancentrum-hu/start-site!))
(defn render-site! [] (karavancentrum-hu/render-site!))
