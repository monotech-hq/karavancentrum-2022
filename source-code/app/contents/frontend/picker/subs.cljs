
(ns app.contents.frontend.picker.subs
    (:require [x.app-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-content-body
  ; @param (keyword) picker-id
  ;
  ; @return (string)
  [db [_ picker-id]]
  (get-in db [:contents :content-picker/content-bodies picker-id]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-content?
  ; @param (keyword) picker-id
  ; @param (map) picker-props
  ;  {:value-path}
  ;
  ; @return (boolean)
  [db [_ picker-id {:keys [value-path]}]]
  ; Ha a content-picker komponens picker-props paramétere megváltozik, akkor
  ; a content-picker-preview-body komponens component-did-update életciklusa
  ; megtörténik és ebben az esetben nem szükséges újra letölteni a tartalmat.
  (let [last-content   (get-in db [:contents :content-picker/meta-items picker-id :picked-content])
        picked-content (get-in db value-path)]
       (not= picked-content last-content)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-sub :contents.content-picker/get-content-body get-content-body)
