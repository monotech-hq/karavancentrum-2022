
(ns app.vehicles.frontend.editor.subs
    (:require [app.vehicles.mid.handler.helpers :as handler.helpers]
              [x.app-core.api                   :as a :refer [r]]
              [x.app-router.api                 :as router]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn get-vehicle-public-link
  [db _]
  (let [vehicle-name (get-in db [:vehicles :editor/edited-item :name])
        public-link  (handler.helpers/vehicle-public-link vehicle-name)]
       (r router/use-app-domain db public-link)))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(a/reg-sub :vehicles.editor/get-vehicle-public-link get-vehicle-public-link)
