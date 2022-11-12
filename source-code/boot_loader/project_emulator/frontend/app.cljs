
(ns boot-loader.project-emulator.frontend.app
    (:require [boot-loader.karavancentrum-hu.frontend.app :as karavancentrum-hu]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn start-app!  [] (karavancentrum-hu/start-app!))
(defn render-app! [] (karavancentrum-hu/render-app!))
