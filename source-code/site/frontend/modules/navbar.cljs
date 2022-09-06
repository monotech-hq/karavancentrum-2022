
(ns site.frontend.modules.navbar
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-elements.api :as elements]
   [x.app-components.api :as components]
   [x.app-environment.api :as environment]

   [site.frontend.components.api :as comp]))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn get-layout! [db]
  (let [viewport-width (r environment/get-viewport-width db)
        threshold      (> 400 viewport-width)]
    (if threshold
      "mobile"
      "desktop")))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; -- Subscriptions ------------------------------------------------------------

(defn get-view-props [db _]
  {:layout (get-layout! db)

   :menu {:in (get-in db [::menu :in] false)}})

(a/reg-sub ::get-view-props get-view-props)

;; -- Subscriptions ------------------------------------------------------------
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; -- Components ---------------------------------------------------------------

(defn nav-logo [{:keys [src]}]
  [:div#logo
   [:a {:href "/"}
    [:img {:src src}]]])

(defn menu [items]
  [:div#menu
   (map-indexed (fn [idx item]
                  ^{:key (str "Navbar-" idx)}
                  [:<> item])
     items)])

(defn open-button [layout]
  (if (= "mobile" layout)
    [:div#navbar--menu-btn
     [:button {:on-click #(a/dispatch [:open-sidebar! :nav-menu])}
      [elements/icon {:icon :menu :size :xl}]]]))

(defn navbar-desktop [config items {:keys [layout] :as view-props}]
  [:nav#navbar {:data-layout layout}
   [:div#navbar--container
    [nav-logo (:logo config)]
    [menu items]]])

(defn navbar-mobile [config items {:keys [layout] :as view-props}]
  [:<>
   [:nav#navbar {:data-layout layout}
    [:div#navbar--container
     [nav-logo (:logo config)]
     [open-button layout]]
    [comp/sidebar {:id :nav-menu}
     [menu items]]]])


(defn navbar [config items {:keys [layout] :as view-props}]
  (if (= "mobile" layout)
    [navbar-mobile config items view-props]
    [navbar-desktop config items view-props]))

(defn view [config & items]
  ; [navbar config items]
  [components/stated {:component  [navbar config items]
                      :subscriber [::get-view-props]}])

;; -- Components ---------------------------------------------------------------
;; -----------------------------------------------------------------------------
