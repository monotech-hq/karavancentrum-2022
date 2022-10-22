
(ns app.home.frontend.sidebar.views
    (:require [app.home.frontend.handler.config :as handler.config]
              [layouts.sidebar-a.api            :as sidebar-a]
              [mid-fruits.vector                :as vector]
              [re-frame.api                     :as r]
              [x.app-components.api             :as components]
              [x.app-elements.api               :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- label-group-item-icon
  ; @param (map) group-item
  ;  {}
  [{:keys [icon icon-color icon-family]}]
  [elements/icon {:color       icon-color
                  :icon        icon
                  :icon-family icon-family
                  :size        :s
                  :indent      {:horizontal :xs :left :s :right :xxs}}])

(defn- label-group-item-label
  ; @param (map) group-item
  ;  {}
  [{:keys [label]}]
  [elements/label {:color     :invert
                   :content   label
                   :indent    {:right :xl}
                   :font-size :xs}])

(defn- label-group-item
  ; @param (map) group-item
  ;  {}
  [{:keys [icon icon-color icon-family disabled? label on-click] :as group-item}]
  [elements/toggle {:content       [:div {:style {:display :flex}}
                                         [label-group-item-icon  group-item]
                                         [label-group-item-label group-item]]
                    :disabled?     disabled?
                    :on-click      on-click
                    :hover-color   :invert}])

(defn- label-group
  ; @param (metamorphic-content) label
  ; @param (?) label-group
  [label label-group]
  (letfn [(f [item-list group-item]
             (conj item-list [label-group-item group-item]))]
         (reduce f [:<>] label-group)))

(defn- weight-group
  ; @param (integer) horizontal-weight
  ; @param (?) weight-group
  [horizontal-weight weight-group]
  ; XXX#0091 (app.home.frontend.screen.views)
  (let [label-groups (group-by #(-> % :label components/content) weight-group)]
       (letfn [(f [group-list label]
                  (conj group-list [label-group label (get label-groups label)]))]
              (reduce f [:<>] (-> label-groups keys sort)))))

(defn- menu-group-label
  ; @param (metamorphic-content) group-name
  [group-name]
  [elements/label {:color     :invert
                   :content   group-name
                   :font-size :xs
                   :indent    {:left :s :top :xs :right :l}
                   :style     {:opacity ".5"}}])

(defn- menu-group
  ; @param (metamorphic-content) group-name
  [group-name]
  ; XXX#0091 (app.home.frontend.screen.views)
  (let [group-items @(r/subscribe [:home.sidebar/get-menu-group-items group-name])]
       (if (vector/nonempty? group-items)
           [:<> [menu-group-label group-name]
                (let [weight-groups (group-by :horizontal-weight group-items)]
                     (letfn [(f [group-list horizontal-weight]
                                (conj group-list [weight-group horizontal-weight (get weight-groups horizontal-weight)]))]
                            (reduce f [:div {:style {:padding-bottom "12px"}}] (-> weight-groups keys sort))))])))

(defn- menu-groups
  []
  ; XXX#0091 (app.home.frontend.screen.views)
  (letfn [(f [group-list group-name]
             (conj group-list [menu-group group-name]))]
         (reduce f [:<>] handler.config/GROUP-ORDER)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  ; @param (keyword) sidebar-id
  [_]
  (if-let [viewport-large @(r/subscribe [:environment/viewport-large?])]
          [menu-groups]))

(defn view
  ; @param (keyword) sidebar-id
  [sidebar-id]

  ; TEMP
  ; BUG
  (let [js-build @(r/subscribe [:router/get-current-js-build])]
       (if (= js-build :app)

           [sidebar-a/layout sidebar-id
                             {:content #'view-structure}])))
