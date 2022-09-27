
(ns app.common.frontend.item-selector.views
    (:require [x.app-core.api     :as a]
              [x.app-elements.api :as elements]))

;; -- Footer components -------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn discard-selection-button
  ; @param (keyword) lister-id
  ; @param (map) footer-props
  ;  {:on-discard (metamorphic-event)
  ;   :selected-item-count (integer)}
  ;
  ; @usage
  ;  [common/discard-selection-button :my-lister {...}]
  [_ {:keys [on-discard selected-item-count]}]
  [elements/button ::discard-selection-button
                   {:disabled?     (< selected-item-count 1)
                    :font-size     :xs
                    :icon          :close
                    :icon-position :right
                    :indent        {:horizontal :xxs :right :xxs}
                    :on-click      on-discard
                    :label {:content :n-items-selected :replacements [selected-item-count]}}])

(defn item-selector-footer
  ; @param (keyword) lister-id
  ; @param (map) footer-props
  ;  {:on-discard (metamorphic-event)
  ;   :selected-item-count (integer)}
  ;
  ; @usage
  ;  [common/item-selector-footer :my-lister {...}]
  [lister-id footer-props]
  [elements/row ::item-selection-footer
                {:content          [discard-selection-button lister-id footer-props]
                 :horizontal-align :right}])

;; -- Control-bar components --------------------------------------------------
;; ----------------------------------------------------------------------------

(defn search-items-field
  ; @param (keyword) lister-id
  ; @param (map) bar-props
  ;  {:search-field-placeholder (metamorphic-content)(opt)
  ;   :search-keys (keywords in vector)}
  ;
  ; @usage
  ;  [common/search-items-field :my-lister {...}]
  [lister-id {:keys [search-field-placeholder search-keys]}]
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? lister-id])
        search-event [:item-lister/search-items! lister-id {:search-keys search-keys}]]
       [:div {:style {:flex-grow 1}}
             [elements/search-field ::search-items-field
                                    {:autoclear?    true
                                     :disabled?     lister-disabled?
                                     :indent        {:horizontal :xxs :left :xxs}
                                     :on-empty      search-event
                                     :on-type-ended search-event
                                     :placeholder   search-field-placeholder}]]))

(defn order-by-icon-button
  ; @param (keyword) lister-id
  ; @param (map) bar-props
  ;  {:order-by-options (namespaced keywords in vector)}
  ;
  ; @usage
  ;  [common/order-by-icon-button :my-lister {...}]
  [lister-id {:keys [order-by-options]}]
  (let [lister-disabled? @(a/subscribe [:item-lister/lister-disabled? lister-id])]
       [elements/icon-button ::order-by-icon-button
                             {:border-radius :s
                              :disabled?     lister-disabled?
                              :hover-color   :highlight
                              :on-click      [:item-lister/choose-order-by! lister-id {:order-by-options order-by-options}]
                              :preset        :order-by}]))

(defn item-selector-control-bar
  ; @param (keyword) lister-id
  ; @param (map) bar-props
  ;  {:order-by-options (namespaced keywords in vector)
  ;   :search-field-placeholder (metamorphic-content)(opt)
  ;   :search-keys (keywords in vector)}
  ;
  ; @usage
  ;  [common/item-selector-control-bar :my-lister {...}]
  [lister-id bar-props]
  [elements/row ::item-selection-control-bar
                {:content [:<> [search-items-field   lister-id bar-props]
                               [order-by-icon-button lister-id bar-props]]}])

;; -- Ghost-view components ---------------------------------------------------
;; ----------------------------------------------------------------------------

(defn item-selector-body-ghost-view
  ; @param (keyword) lister-id
  ; @param (map) view-props
  ;
  ; @usage
  ;  [common/item-selector-body-ghost-view :my-lister {...}]
  [_ {:keys []}]
  [:div {:style {:padding "0 12px" :width "100%"}}
        [:div {:style {:display "flex" :flex-direction "column" :width "100%" :grid-row-gap "24px"}}
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {}}]]
              [:div {:style {:flex-grow 1}} [elements/ghost {:height :l :indent {}}]]]])
