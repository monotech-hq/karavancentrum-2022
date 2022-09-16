
(ns app.contents.frontend.tabs.main-page
  (:require
    [x.app-core.api       :as a]
    [plugins.api :as plugins]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn main-page []
  (let [path [:contents :config-handler/edited-item :main-page]]
    [:<>
     [plugins/text-editor {:value-path path}]]))

(defn view [surface-id]
  [main-page])

;; ---- Components ----
;; -----------------------------------------------------------------------------
