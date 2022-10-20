
(ns site.karavancentrum.pages.main-page.frontend.sections.contacts
    (:require [app.contents.frontend.api :as contents]
              [mid-fruits.href           :as href]
              [re-frame.api              :as r]
              [utils.api                 :refer [html->hiccup]]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn contact-information
  []
  (let [contacts-data-information @(r/subscribe [:db/get-item [:site :contents :contacts-data-information]])]
       [:div.kc-contact-information [contents/content-preview {:items [contacts-data-information] :color :default :font-size :inherit}]]))

(defn contact-group
  [{:keys [label phone-numbers email-addresses]}]
  [:div.kc-contact-group (if label [:div.kc-contact-group--label label ":"])
                         (letfn [(f [%1 %2] (conj %1 [:a.kc-contact-group--value {:href (href/phone-number %2)} (str %2)]))]
                                (reduce f [:<>] phone-numbers))
                         (letfn [(f [%1 %2] (conj %1 [:a.kc-contact-group--value {:href (href/email-address %2)} (str %2)]))]
                                (reduce f [:<>] email-addresses))])

(defn contact-groups
  []
  (let [contact-groups @(r/subscribe [:db/get-item [:site :config :contact-groups]])]
       (letfn [(f [contact-groups group-props]
                  (conj contact-groups [contact-group group-props]))]
              (reduce f [:<>] contact-groups))))

(defn address-information
  []
  (let [address-data-information @(r/subscribe [:db/get-item [:site :contents :address-data-information]])]
       [:div.kc-address-information [contents/content-preview {:items [address-data-information] :color :default :font-size :inherit}]]))

(defn address-group
  [{:keys [label company-address]}]
  (let [company-address-link (href/address company-address)]
       [:div.kc-address-group (if label [:div.kc-address-group--label label ":"])
                              [:a.kc-address-group--value {:href company-address-link :target "_blank"}
                                                          company-address]]))

(defn address-groups
  []
  (let [address-groups @(r/subscribe [:db/get-item [:site :config :address-groups]])]
       (letfn [(f [address-groups group-props]
                  (conj address-groups [address-group group-props]))]
              (reduce f [:<>] address-groups))))

(defn contacts
  []
  [:<> [:section [:div#kc-contacts [:p.kc-section-title "Kapcsolat"]
                                   [:div#kc-contact-groups [contact-groups]
                                                           [contact-information]
                                                           [address-groups]]
                                   [:div                [address-information]]]
                 [:section#kc-contacts--background]]])

(defn view
  []
  [contacts])
