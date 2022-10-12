
(ns app.views.frontend.privacy-policy.views
    (:require [app.common.frontend.api :as common]
              [mid-fruits.lorem-ipsum  :as lorem-ipsum]
              [layouts.surface-a.api   :as surface-a]
              [re-frame.api            :as r]
              [x.app-elements.api      :as elements]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- content
  [_]
  [elements/text ::content
                 {:content lorem-ipsum/LONG
                  :indent  {:top :xxl :vertical :s}}])

(defn- breadcrumbs
  [_]
  [elements/breadcrumbs ::breadcrumbs
                        {:crumbs [{:label :app-home
                                   :route "/@app-home"}
                                  {:label :privacy-policy}]
                         :indent {:left :xs}}])

(defn- title
  [surface-id]
  [common/surface-label surface-id
                        {:label :privacy-policy}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  [surface-id]
  (if-let [page-loaded? @(r/subscribe [:db/get-item [:views :privacy-policy/page-loaded?]])]
          [:<> [title       surface-id]
               [breadcrumbs surface-id]
               [content     surface-id]
               [content     surface-id]
               [content     surface-id]]
          [common/surface-box-layout-ghost-view :views.privacy-policy/view {:breadcrumb-count 1}]))

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
