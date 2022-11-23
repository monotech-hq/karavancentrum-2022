
(ns site.components.frontend.contacts.views
    (:require [app.contents.frontend.api :as contents]
              [candy.api                 :refer [return]]
              [elements.api              :as elements]
              [random.api                :as random]
              [re-frame.api              :as r]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- contacts-data-information
  ; @param (keyword) component-id
  ; @param (map) component-props
  [_ _]
  (let [contacts-data-information @(r/subscribe [:x.db/get-item [:site :website-impressum :contacts-data-information]])]
       (if (contents/nonblank? contacts-data-information)
           [:div {:id :mt-contacts--contacts-data-information} contacts-data-information])))

(defn- contact-group
  ; @param (keyword) component-id
  ; @param (map) component-props
  ; @param (map) group-props
  ;  {:email-addresses (strings in vector)(opt)
  ;   :label (string)(opt)
  ;   :phone-numbers (strings in vector)(opt)}
  [_ _ {:keys [email-addresses label phone-numbers]}]
  [:div {:class :mt-contacts--contact-group}
        (if label [:div {:class :mt-contacts--contact-group-label} label])
        (letfn [(f [phone-numbers phone-number]
                   (if phone-number (conj   phone-numbers [:div {:class :mt-contacts--phone-number} phone-number])
                                    (return phone-numbers)))]
               (reduce f [:<>] phone-numbers))
        (letfn [(f [email-addresses email-address]
                   (if email-address (conj   email-addresses [:div {:class :mt-contacts--email-address} email-address])
                                     (return email-addresses)))]
               (reduce f [:<>] phone-numbers))])

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
  (let [address-data-information @(r/subscribe [:x.db/get-item [:site :website-impressum :address-data-information]])]
       (if (contents/nonblank? contacts-data-information)
           [:div {:id :mt-contacts--address-data-information} address-data-information])))

(defn- address-group
  ; @param (keyword) component-id
  ; @param (map) component-props
  ; @param (map) group-props
  ;  {:company-address (string)(opt)
  ;   :label (string)(opt)}
  [_ _ {:keys [company-address label]}]
  [:div {:class :mt-contacts--address-group}
        (if label           [:div {:class :mt-contacts--address-group-label} label])
        (if company-address [:div {:class :mt-contacts--company-address}     company-address])])

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
