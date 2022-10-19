
(ns app.home.frontend.sidebar.views
    (:require [app.home.frontend.handler.config :as handler.config]
              [layouts.sidebar-a.api            :as sidebar-a]
              [mid-fruits.vector                :as vector]
              [re-frame.api                     :as r]
              [x.app-components.api             :as components]
              [x.app-elements.api               :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- label-group-item-content
  ; @param (map) group-item
  ;  {}
  [{:keys [icon icon-color icon-family label]}]
  [:div {:style {:display :flex}}
        [elements/icon {:color       icon-color
                        :icon        icon
                        :icon-family icon-family
                        ;:indent      {:horizontal :xs}
                        :size        :s

                        :indent  {:horizontal :xs :left :s :right :xxs}}]
        [elements/label {:content label
                         :indent {:right :l}
                         :font-size :xs}]])



(defn- label-group-item
  ; @param (map) group-item
  ;  {}
  [{:keys [icon icon-color icon-family disabled? label on-click] :as group-item}]
  [elements/toggle {:content [label-group-item-content group-item]
                    :disabled?     disabled?
                    :on-click      on-click
                    :hover-color   :highlight}])
                    ;:horizontal-align :left}])
                    ;:min-width     :s}])
                    ;:indent        {:vertical :xs}}])

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

(defn- menu-group
  ; @param (keyword) group-name
  [group-name]
  ; XXX#0091 (app.home.frontend.screen.views)
  (let [group-items @(r/subscribe [:home.sidebar/get-menu-group-items group-name])]
       (if (vector/nonempty? group-items)
           (let [weight-groups (group-by :horizontal-weight group-items)]
                (letfn [(f [group-list horizontal-weight]
                           (conj group-list [weight-group horizontal-weight (get weight-groups horizontal-weight)]))]
                       (reduce f [:div {:style {:padding "12px 0"}}] (-> weight-groups keys sort)))))))

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
  [:<> [elements/horizontal-separator {:size :m}]
       [menu-groups]])

(defn view
  ; @param (keyword) sidebar-id
  [sidebar-id]
  [sidebar-a/layout sidebar-id
                    {:content #'view-structure}])
