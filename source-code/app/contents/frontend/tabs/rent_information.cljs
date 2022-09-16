
(ns app.contents.frontend.tabs.rent-information
  (:require
    [x.app-core.api       :as a]
    [plugins.api :as plugins]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn rent-information []
  (let [path [:contents :config-handler/edited-item :rent-informations]]
    [:<>
     [plugins/text-editor {:value-path path}]]))

(defn view []
  [rent-information])

;; ---- Components ----
;; -----------------------------------------------------------------------------
