
(ns app.vehicles.frontend.editor.subs
    (:require [mid-fruits.normalize :as normalize]
              [x.app-core.api       :as a :refer [r]]
              [x.app-router.api     :as router]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn get-vehicle-public-link
  [db _]
  (let [vehicle-name    (get-in db [:vehicles :editor/edited-item :name])
        normalized-name (normalize/clean-url vehicle-name)
        public-link     (str "/berelheto-jarmuveink/"normalized-name)]
       (r router/use-app-domain db public-link)))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(a/reg-sub :vehicles.editor/get-vehicle-public-link get-vehicle-public-link)
