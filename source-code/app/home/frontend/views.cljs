
(ns app.home.frontend.views
    (:require [app.common.frontend.api :as common]
              [layouts.surface-a.api   :as surface-a]
              [mid-fruits.css          :as css]
              [x.app-core.api          :as a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- home-title
  []
  (let [app-title @(a/subscribe [:core/get-app-config-item :app-title])]
       [common/surface-label nil {:label app-title}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- user-profile-picture
  []
  (let [user-profile-picture @(a/subscribe [:user/get-user-profile-picture])]
       [:div.x-user-profile-picture {:style {:backgroundImage     (css/url user-profile-picture)
                                             :background-color    (css/var "background-color-highlight")
                                             :border-radius       "50%";
                                             :background-position "center"
                                             :background-repeat   "no-repeat"
                                             :background-size     "90%"
                                             :overflow            "hidden"
                                             :height              "60px"
                                             :width               "60px"}}]))

(defn- user-name-label
  []
  (let [user-name @(a/subscribe [:user/get-user-name])]
       [elements/label ::user-name-label
                       {:content     user-name
                        :font-size   :s
                        :font-weight :extra-bold
                        :indent      {:left :xs}
                        :style       {:line-height "18px"}}]))

(defn- user-email-address-label
  []
  (let [user-email-address @(a/subscribe [:user/get-user-email-address])]
       [elements/label ::user-email-address-label
                       {:color     :muted
                        :content   user-email-address
                        :font-size :xs
                        :indent    {:left :xs}
                        :style     {:line-height "18px"}}]))

(defn- user-card
  []
  [:div {:style {:display :flex}}
        [:div {}
              [user-profile-picture]]
        [:div {:style {:display :flex :flex-direction :column :justify-content :center}}
              [user-name-label]
              [user-email-address-label]]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- header
  []
  [:<> [elements/horizontal-separator {:size :xxl}]
       [home-title]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn menu-group-header
  [group-name]
  [elements/label {:color            :muted
                   :content          group-name
                   :font-size        :l
                   :horizontal-align :left
                   :indent           {:left :xs :top :xxl}}])

(defn menu-group-item
  [{:keys [icon label on-click]}]
  [elements/card {:border-radius :m
                  :content [elements/label {:icon icon :content label :indent {:all :xs}}]
                  :on-click on-click
                  :horizontal-align :left
                  :hover-color :highlight
                  :indent {:vertical :xs}}])

(defn menu-group-items
  [group-items]
  [:div {:style {:display "flex" :column-gap "48px" :flex-wrap "wrap"}}
        (map (fn [%] ^{:key (random-uuid)} [menu-group-item %])
             (sort-by :label group-items))])

(defn menu-group
  [[group-name group-items]]
  [:div {}
        [menu-group-header group-name]
        [menu-group-items group-items]])

(defn menu-groups
  []
  (let [menu-items @(a/subscribe [:home/get-menu-items])]
       [:<> (map (fn [%] ^{:key (random-uuid)} [menu-group %])
                 (group-by :group menu-items))]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view-structure
  [surface-id]
  [:div {:style {:width "var(--content-width-l)" :padding "0 0 48px 0"}}
        [header]
        [menu-groups]])

(defn view
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
