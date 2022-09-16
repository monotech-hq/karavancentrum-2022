
(ns app.contents.frontend.tabs.about-us
  (:require
    [x.app-core.api :as a]
    [plugins.api :as plugins]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn about-us []
  (let [path [:contents :config-handler/edited-item :about-us]]
    [:<>
     [plugins/text-editor {:value-path path}]]))

(defn view []
  [about-us])

;; ---- Components ----
;; -----------------------------------------------------------------------------
