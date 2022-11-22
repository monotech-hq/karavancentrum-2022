
(ns project.ui.backend.api
    (:require [project.ui.backend.loading-screen :as loading-screen]
              [project.ui.backend.main           :as main]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; project.ui.backend.loading-screen
(def loading-screen loading-screen/view)

; project.ui.backend.main
(def main main/view)
