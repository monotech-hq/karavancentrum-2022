
(ns site.components.frontend.contacts.views
    (:require [elements.api :as elements]
              [random.api   :as random]
              [re-frame.api :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn contacts
  ; @param (keyword) component-id
  ; @param (map) component-props
  ;  {}
  [_ {:keys []}]
  [:div {:id :mt-contacts}])

(defn component
  ; @param (keyword)(opt) component-id
  ; @param (map) component-props
  ;  {}
  ;
  ; @usage
  ;  [contacts {...}]
  ;
  ; @usage
  ;  [contacts :my-contacts {...}]
  ([component-props]
   [component (random/generate-keyword) component-props])

  ([component-id component-props]
   [contacts component-id component-props]))
