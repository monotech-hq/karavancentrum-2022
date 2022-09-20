
(ns app.contents.frontend.picker.events
    (:require [mid-fruits.map :refer [dissoc-in]]
              [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-content!
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:value-path (vector)}
  ;
  ; @return (map)
  [db [_ picker-id {:keys [value-path]}]]
  (let [picked-content (get-in db value-path)]
       (-> db (dissoc-in [:contents :content-picker/content-bodies picker-id])
              (assoc-in  [:contents :content-picker/meta-items     picker-id :picked-content] picked-content))))

(defn receive-content!
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ; @param (map) server-response
  ;  {:contents.content-picker/get-content (namespaced map)}
  ;
  ; @return (map)
  [db [_ picker-id _ server-response]]
  (let [content-body (-> server-response :contents.content-picker/get-content :content/body)]
       (assoc-in db [:contents :content-picker/content-bodies picker-id] content-body)))
