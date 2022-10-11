
(ns site.karavancentrum.wrapper
    (:require [re-frame.api                       :as r]
              [site.karavancentrum.modules.api    :as modules]
              [site.karavancentrum.components.api :as components]
              [utils.api                          :as utils]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn logo []
  [:a {:href "/" :style {:text-decoration "none"}}
    [:div#kc-logo-and-company-name
     [:div#kc-company-name-and-slogan
      [:div#kc-company-name "Karaván Centrum"]
      [:div#kc-company-slogan "Lakóautók és kempingcikkek"]]]])

(defn navbar-item [{:keys [scroll-target] :as config} label]
  [:a.kc-link.kc-effect--underline (merge {:style {"--underline-color" "black"}
                                           :on-mouse-up #(.blur (.-target %))
                                           :on-click    #(r/dispatch [:utils/scroll-into scroll-target])}
                                          (dissoc config :scroll-target))
   label])

(defn navbar []
  [modules/navbar {:threshold 800 :align-x :right ;:max-width 1200
                                                :logo [logo]}
   [navbar-item {:href "/berbeadas"}                           "Bérbeadás"]
   [navbar-item {:href "/ertekesites"}                         "Értékesítés"]
   [navbar-item {:href "/"}                                    "Webáruház"]
   [navbar-item {:href "/kapcsolat" :scroll-target "contacts"} "Kapcsolat"]])

(defn header []
  [navbar])

(defn site-wrapper [ui-structure]
  [:div#karavancentrum
   [header]
   [ui-structure]])

(defn view [ui-structure]
  [site-wrapper ui-structure])
