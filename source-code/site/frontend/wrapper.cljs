
(ns site.frontend.wrapper
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-router.api :as router]
   [dom.api :as dom]
   [x.app-environment.api :as env]
   [x.app-components.api :as components]

   [mid-fruits.string :as string]

   [site.frontend.modules.api :as site.modules]
   [site.frontend.components.api :as site.components]))


;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn route-path->breadcrumbs [route-path]
  (string/remove-part route-path "/utanfutok/"))

(defn scroll-into [element-id config]
  (let [element (.getElementById js/document element-id)]
    (.scrollIntoView element (clj->js config))))

(defn scrolled-to-top? []
  ;(<= (dom/get-scroll-y) 50)
  true)

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn get-view-props [db _]
  (let [route-path (r router/get-current-route-path db)]
    {:scrolled-to-top? (scrolled-to-top?)
     :route-path   route-path}))

   ;:layout (get-layout! db)})

(a/reg-sub ::get-view-props get-view-props)

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn navbar []
  [site.modules/navbar {:logo {:src "/images/logo.png"}}
   [:a.link.effect--underline
    {:href     "/"
     :on-click #(a/dispatch [:close-sidebar! :nav-menu])}
    "Item 1"]
   [:a.link.effect--underline
    {:href     ""
     :on-click #(do
                  (a/dispatch [:close-sidebar! :nav-menu])
                  (scroll-into "footer"
                            {:behavior "smooth"
                             :block "start"}))}
    "Item 2"]])

(defn footer []
  [site.modules/footer
   [:div [site.components/link {:prefix "mailto:"} "iroda@karavancentrum.hu"]]
   [:div [site.components/link {:href "hali"} "karavancentrum.hu"]]
   [:div [:button.effect--underline {:on-click #(scroll-into "x-app-container"
                                                             {:behavior "smooth"
                                                              :block "start"})}
           "Vissza az oldal tetejére"]]])

(defn site-wrapper [_ [ui-structure {:keys [scrolled-to-top?] :as view-props}]]
  [:div#karavancentrum {:data-scrolled-to-top scrolled-to-top?}
   [navbar]
   [ui-structure]
   [footer]])

(defn view [ui-structure]
  [components/stated
   {:render-f   #'site-wrapper
    :base-props [ui-structure]
    :subscriber [::get-view-props]}])

;; ---- Components ----
;; -----------------------------------------------------------------------------
