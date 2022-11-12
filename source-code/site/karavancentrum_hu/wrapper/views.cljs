
(ns site.karavancentrum-hu.wrapper.views
    (:require [mid-fruits.uri               :as uri]
              [re-frame.api                 :as r]
              [site.components.frontend.api :as components]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn sidebar-menu
  []
  (let [webshop-link @(r/subscribe [:db/get-item [:site :content :webshop-link]])
        webshop-link  (uri/valid-uri webshop-link)]
       [:div#kc-sidebar--menu-items
         [:a.kc-sidebar--menu-item.mt-effect--underline {:href "/berbeadas"   :on-click #(r/dispatch [:site.components/hide-sidebar!])}
                                                        "Bérbeadás"]
         [:a.kc-sidebar--menu-item.mt-effect--underline {:href "/ertekesites" :on-click #(r/dispatch [:site.components/hide-sidebar!])}
                                                        "Értékesítés"]
         [:a.kc-sidebar--menu-item.mt-effect--underline {:href webshop-link   :on-click #(r/dispatch [:site.components/hide-sidebar!] :target "_blank")}
                                                        "Webáruház"]
         [:a.kc-sidebar--menu-item.mt-effect--underline {:href "/kapcsolat"   :on-click #(r/dispatch [:site.components/hide-sidebar!])}
                                                        "Kapcsolat"]]))

(defn sidebar
  []
  [components/sidebar {:content #'sidebar-menu}])

(defn company-name-and-slogan
  []
  (let [company-name   @(r/subscribe [:db/get-item [:site :config :company-name]])
        company-slogan @(r/subscribe [:db/get-item [:site :config :company-slogan]])]
       [:a {:href "/" :style {:text-decoration "none"}}
           [:div#kc-navbar--company-name-and-slogan [:div#kc-navbar--company-name   company-name]
                                                    [:div#kc-navbar--company-slogan company-slogan]]]))

(defn header
  []
  (let [webshop-link @(r/subscribe [:db/get-item [:site :content :webshop-link]])
        webshop-link  (uri/valid-uri webshop-link)]
       [components/navbar {:logo #'company-name-and-slogan
                           :menu-items [{:href "/berbeadas"   :label "Bérbeadás"   :class :mt-effect--underline}
                                        {:href "/ertekesites" :label "Értékesítés" :class :mt-effect--underline}
                                        {:href webshop-link   :label "Webáruház"   :class :mt-effect--underline :target "_blank"}
                                        {:href "/kapcsolat"   :label "Kapcsolat"   :class :mt-effect--underline}]
                           :on-menu [:site.components/show-sidebar!]
                           :threshold 800}]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn wrapper
  [ui-structure]
  [:div#kc [ui-structure]
           [header]
           [sidebar]])

(defn view
  [ui-structure]
  [wrapper ui-structure])
