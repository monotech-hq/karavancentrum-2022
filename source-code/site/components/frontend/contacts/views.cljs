
(ns site.components.frontend.contacts.views
    (:require [elements.api :as elements]
              [random.api   :as random]
              [re-frame.api :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts-data-information
  ; @param (keyword) component-id
  ; @param (map) component-props
  [_ _]
  [:div {:id :mt-contacts--contacts-data-information}])
  ;(let [contacts-data-information @(r/subscribe [:x.db/get-item [:site :website-impressum :contacts-data-information]])]
  ;     [:div.kc-contact-information [contents/content-preview {:item-link contacts-data-information :color :default :font-size :inherit :font-weight :inherit}]]])

(defn- contact-group
  ; @param (keyword) component-id
  ; @param (map) component-props
  ; @param (map) group-props
  [_ _ group-props]
  [:div {:class :mt-contacts--contact-group}])

(defn- contact-groups
  ; @param (keyword) component-id
  ; @param (map) component-props
  [component-id component-props]
  (let [contact-groups @(r/subscribe [:x.db/get-item [:site :website-impressum :contact-groups]])]
       [:div {:id :mt-contacts--contact-groups}
             (letfn [(f [groups group-props]
                        (conj groups [contact-group component-id component-props group-props]))]
                    (reduce f [:<>] contact-groups))]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- address-data-information
  ; @param (keyword) component-id
  ; @param (map) component-props
  [_ _]
  [:div {:id :mt-contacts--address-data-information}])

(defn- address-group
  ; @param (keyword) component-id
  ; @param (map) component-props
  ; @param (map) group-props
  [_ _ group-props]
  [:div {:class :mt-contacts--address-group}])

(defn- address-groups
  ; @param (keyword) component-id
  ; @param (map) component-props
  [component-id component-props]
  (let [address-groups @(r/subscribe [:x.db/get-item [:site :website-impressum :address-groups]])]
       [:div {:id :mt-contacts--address-groups}
             (letfn [(f [groups group-props]
                        (conj groups [address-group component-id component-props group-props]))]
                    (reduce f [:<>] address-groups))]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts
  ; @param (keyword) component-id
  ; @param (map) component-props
  [component-id component-props]
  [:div {:id :mt-contacts}
        [contact-groups            component-id component-props]
        [contacts-data-information component-id component-props]
        [address-groups            component-id component-props]
        [address-data-information  component-id component-props]])

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
