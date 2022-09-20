
(ns app.contents.frontend.picker.effects
    (:require [app.contents.frontend.picker.helpers :as picker.helpers]
              [app.contents.frontend.picker.events  :as picker.events]
              [app.contents.frontend.picker.subs    :as picker.subs]
              [x.app-core.api                       :as a :refer [r]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-event-fx
  :contents.content-picker/request-content!
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:value-path (vector)}
  (fn [{:keys [db]} [_ picker-id {:keys [value-path] :as picker-props}]]
      (if (r picker.subs/request-content? db picker-id picker-props)
          (let [picked-content (get-in db value-path)]
               {:db       (r picker.events/request-content! db picker-id picker-props)
                :dispatch [:pathom/send-query! picker-id
                                               {:on-success [:contents.content-picker/receive-content! picker-id picker-props]
                                                :query      (picker.helpers/request-content-query picked-content)}]}))))

(a/reg-event-fx
  :contents.content-picker/receive-content!
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ; @param (map) server-response
  (fn [{:keys [db]} [_ picker-id picker-props server-response]]
      {:db (r picker.events/receive-content! db picker-id picker-props server-response)}))
