
(ns app.views.frontend.privacy-policy.views
    (:require [app.common.frontend.api :as common]
              [mid-fruits.lorem-ipsum  :as lorem-ipsum]
              [layouts.surface-a.api   :as surface-a]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content
  [_]
  [elements/text ::content
                 {:content lorem-ipsum/LONG
                  :indent  {:vertical :xs :top :xxl}}])

(defn- title
  [surface-id]
  [:<> [elements/horizontal-separator {:size :xxl}]
       [common/surface-label surface-id
                             {:label :privacy-policy}]])

(defn- go-back-button
  [_]
  [:div {:style {:display :flex :justify-content :center}}
        [elements/button ::go-back-button
                         {:border-radius :s
                          :hover-color   :highlight
                          :indent        {:top :m}
                          :label         :back!
                          :on-click      [:router/go-back!]}]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  [surface-id]
  [:<> [title          surface-id]
       [content        surface-id]
       [content        surface-id]
       [content        surface-id]
       [go-back-button surface-id]
       [elements/horizontal-separator {:size :xxl}]])

(defn view
  [surface-id]
  ; Remove stored cookies button
  ; Multilingual content
  ;
  ; WARNING!
  ; Az Adatvédelmi irányelvek tartalmát jelenítsd meg a cookie-consent popup felületen,
  ; ahelyett, hogy erre az oldalra irányítanád a privacy-policy gombbal a felhasználót!
  ; Erről az oldalról tovább lehet navigálni az applikáció más részire anélkül, hogy
  ; elfogadná a cookie-consent tartalmát!
  [surface-a/layout surface-id
                    {:content [view-structure surface-id]}])
