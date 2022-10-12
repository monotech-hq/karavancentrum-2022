
(ns app.home.frontend.screen.views
    (:require [app.common.frontend.api          :as common]
              [app.home.frontend.handler.config :as handler.config]
              [layouts.surface-a.api            :as surface-a]
              [mid-fruits.css                   :as css]
              [mid-fruits.vector                :as vector]
              [re-frame.api                     :as r]
              [x.app-components.api             :as components]
              [x.app-elements.api               :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- user-profile-picture
  []
  (let [user-profile-picture @(r/subscribe [:user/get-user-profile-picture])]
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
  (let [user-name @(r/subscribe [:user/get-user-name])]
       [elements/label ::user-name-label
                       {:content     user-name
                        :font-size   :s
                        :font-weight :extra-bold
                        :indent      {:left :xs}
                        :style       {:line-height "18px"}}]))

(defn- user-email-address-label
  []
  (let [user-email-address (r/subscribe [:user/get-user-email-address])]
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

(defn- label-group-item-content
  [{:keys [icon icon-color icon-family label]}]
  [:div {:style {:display :flex}}
        [elements/icon {:color       icon-color
                        :icon        icon
                        :icon-family icon-family
                        :indent      {:horizontal :xs}
                        :size        :l}]
        [elements/label {:content label
                         :indent  {}}]])

(defn- label-group-item
  [{:keys [disabled? on-click] :as group-item}]
  [elements/card {:border-radius    :m
                  :content          [label-group-item-content group-item]
                  :disabled?        disabled?
                  :on-click         on-click
                  :horizontal-align :left
                  :hover-color      :highlight
                  :min-width        :s
                  :indent           {:vertical :xs}}])

(defn- label-group
  [label label-group]
  (letfn [(f [group-item-list group-item]
             (conj group-item-list [label-group-item group-item]))]
         (reduce f [:<>] label-group)))

(defn- horizontal-group
  [horizontal-weight horizontal-group]
  ; Az azonos vertical-group csoportokban és azon belül is azonos horizontal-group
  ; csoportokban felsorolt menü elemek a label tulajdonságuk szerinti kisebb
  ; csoportokban vannak felsorolva.
  (let [label-groups (group-by #(-> % :label components/content) horizontal-group)]
       (letfn [(f [label-group-list label]
                  (conj label-group-list [label-group label (get label-groups label)]))]
              (reduce f [:<>] (-> label-groups keys sort)))))

(defn- vertical-group
  [group-name]
  ; Az azonos vertical-group csoportokban felsorolt menü elemek a horizontal-weight
  ; tulajdonságuk szerinti kisebb csoportokban vannak felsorolva.
  (let [group-items @(r/subscribe [:home.screen/get-menu-group-items group-name])]
       (if (vector/nonempty? group-items)
           [common/surface-box {:content [:div {:style {:display "flex" :flex-wrap "wrap" :grid-row-gap "12px" :padding "12px 0"}}
                                               (let [horizontal-groups (group-by :horizontal-weight group-items)]
                                                    (letfn [(f [horizontal-group-list horizontal-weight]
                                                               (conj horizontal-group-list [horizontal-group horizontal-weight (get horizontal-groups horizontal-weight)]))]
                                                           (reduce f [:<>] (-> horizontal-groups keys sort))))]
                                :indent {:top :m}
                                :label  group-name}])))

(defn- menu-groups
  []
  ; A menü elemek elsődlegesen a group tulajdonságuk szerint csoportosítva
  ; vannak felsorolva a vertical-group csoportokban.
  (letfn [(f [vertical-group-list group-name]
             (conj vertical-group-list [vertical-group group-name]))]
         (reduce f [:<>] handler.config/GROUP-ORDER)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- breadcrumbs
  []
  [common/surface-breadcrumbs :home.screen/view
                              {:crumbs [{:label :app-home}]}])

(defn- label
  []
  (let [app-title @(r/subscribe [:core/get-app-config-item :app-title])]
       [common/surface-label :home.screen/view
                             {:label app-title}]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  ; @param (keyword) surface-id
  [_]
  (if-let [screen-loaded? @(r/subscribe [:db/get-item [:home :screen/screen-loaded?]])]
          [:<> [label]
               [breadcrumbs]
               ;[elements/horizontal-separator {:size :xxl}]
               [menu-groups]]
          [common/surface-box-layout-ghost-view :home.screen/view {:breadcrumb-count 1}]))

(defn view
  ; @param (keyword) surface-id
  [surface-id]
  [surface-a/layout surface-id
                    {:content #'view-structure}])
