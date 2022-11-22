
(ns site.karavancentrum-hu.pages.main-page.frontend.sections.contacts
    (:require [app.contents.frontend.api :as contents]
              [href.api                  :as href]
              [re-frame.api              :as r]
              [uri.api                   :as uri]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn contact-information
  []
  (let [contacts-data-information @(r/subscribe [:x.db/get-item [:site :content :contacts-data-information]])]
       [:div.kc-contact-information [contents/content-preview {:items [contacts-data-information] :color :default :font-size :inherit :font-weight :inherit}]]))

(defn contact-group
  [{:keys [label phone-numbers email-addresses]}]
  [:div.kc-contact-group (if label [:div.kc-contact-group--label label ":"])
                         (letfn [(f [%1 %2] (conj %1 [:a.kc-contact-group--value {:href (href/phone-number %2)} (str %2)]))]
                                (reduce f [:<>] phone-numbers))
                         (letfn [(f [%1 %2] (conj %1 [:a.kc-contact-group--value {:href (href/email-address %2)} (str %2)]))]
                                (reduce f [:<>] email-addresses))])

(defn contact-groups
  []
  (let [contact-groups @(r/subscribe [:x.db/get-item [:site :config :contact-groups]])]
       (letfn [(f [contact-groups group-props]
                  (conj contact-groups [contact-group group-props]))]
              (reduce f [:<>] contact-groups))))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn address-information
  []
  (let [address-data-information @(r/subscribe [:x.db/get-item [:site :content :address-data-information]])]
       [:div.kc-address-information [contents/content-preview {:items [address-data-information] :color :default :font-size :inherit :font-weight :inherit}]]))

(defn address-group
  [{:keys [label company-address]}]
  (let [company-address-link (href/address company-address)]
       [:div.kc-address-group (if label [:div.kc-address-group--label label ":"])
                              [:a.kc-address-group--value {:href company-address-link :target "_blank"}
                                                          company-address]]))

(defn address-groups
  []
  (let [address-groups @(r/subscribe [:x.db/get-item [:site :config :address-groups]])]
       (letfn [(f [address-groups group-props]
                  (conj address-groups [address-group group-props]))]
              (reduce f [:<>] address-groups))))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn social-media-link
  [link icon-class label]
  [:a.kc-social-media-link {:href (uri/valid-uri link) :target "_blank" :title label}
                           [:i {:class icon-class}]
                           [:span (uri/uri->trimmed-uri (str "https://www." link))]])

(defn facebook-links
  []
  (let [facebook-links @(r/subscribe [:x.db/get-item [:site :config :facebook-links]])]
       (letfn [(f [links link] (conj links [social-media-link link [:fab :fa-facebook] "Tovább a Facebook-ra"]))]
              (reduce f [:<>] facebook-links))))

(defn instagram-links
  []
  (let [instagram-links @(r/subscribe [:x.db/get-item [:site :config :instagram-links]])]
       (letfn [(f [links link] (conj links [social-media-link link [:fab :fa-instagram] "Tovább a Instagram-ra"]))]
              (reduce f [:<>] instagram-links))))

(defn youtube-links
  []
  (let [youtube-links @(r/subscribe [:x.db/get-item [:site :config :youtube-links]])]
       (letfn [(f [links link] (conj links [social-media-link link [:fab :fa-youtube] "Tovább a Youtube-ra"]))]
              (reduce f [:<>] youtube-links))))

(defn social-media-links
  []
  [:<> [facebook-links]
       [instagram-links]
       [youtube-links]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn contacts
  []
  [:<> [:div#kc-contacts [:p.kc-section-title "Kapcsolat"]
                         [:div#kc-contact-groups [contact-groups]
                                                 [contact-information]
                                                 [address-groups]]
                         [:div#kc-address-groups [address-information]]
                         [:div#kc-social-media-links [social-media-links]]]
       [:div#kc-contacts--background]])

(defn view
  []
  [:section {:id :contacts}
            [contacts]])
