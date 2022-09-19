
(ns site.wrapper
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-components.api :as components]
   [dom.api :as dom]

   [mid-fruits.string :as string]

   [site.modules.api :as site.modules]
   [site.components.api :as site.components]))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn scroll-into [element-id config]
  (let [element (.getElementById js/document element-id)]
    (.scrollIntoView element (clj->js config))))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn logo []
  [:div#logo-and-company-name
   [:div#logo]
   [:div#company-name-and-slogan
    [:div#company-name "Karaván Centrum"]
    [:div#company-slogan "Lakóautók és kempingcikkek"]]])

(defn navbar []
  [site.modules/navbar {:threshold 800
                        :align-x :right
                        :logo [logo]}
   [:a.link.effect--underline {:href ""} "Bérbeadás"]
   [:a.link.effect--underline {:href ""} "Értékesítés"]
   [:a.link.effect--underline {:href ""} "Webáruház"]
   [:a.link.effect--underline {:href ""} "Kapcsolat"]])

(defn header []
  [navbar])

(defn footer []
  [site.modules/footer
   [:div [site.components/link {:prefix "mailto:"} "iroda@karavancentrum.hu"]]
   [:div [site.components/link {:href "hali"} "karavancentrum.hu"]]])

(defn site-wrapper [ui-structure]
  [:div#karavancentrum
   [header]
   [ui-structure]
   [footer]])

(defn view [ui-structure]
  [site-wrapper
   ui-structure])

;; ---- Components ----
;; -----------------------------------------------------------------------------
