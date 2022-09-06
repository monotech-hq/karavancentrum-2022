
(ns app.site-config.frontend.subs
    (:require [x.app-core.api       :as a :refer [r]]
              [x.app-components.api :as components]
              [x.app-activities.api :as activities]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-description
  [db _]
  (let [modified-at (-> db :site-config :editor-data :site-config/modified-at)]
       (let [actual-timestamp (r activities/get-actual-timestamp db (or modified-at "2021-04-20T00:04:20.123Z"))]
            (components/content {:content :last-modified-at-n
                                 :replacements [actual-timestamp]}))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-sub :site-config/get-description get-description)
