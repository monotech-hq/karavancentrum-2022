
(ns app.views.frontend.terms-of-service.views
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

(defn- breadcrumbs
  [_]
  [elements/breadcrumbs ::breadcrumbs
                        {:crumbs [{:label :app-home
                                   :route "/@app-home"}
                                  {:label :terms-of-service}]
                         :indent {:left :xs}}])

(defn- title
  [surface-id]
  [common/surface-label surface-id
                        {:label :terms-of-service}])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- view-structure
  [surface-id]
  [:<> [title       surface-id]
       [breadcrumbs surface-id]
       [content     surface-id]
       [content     surface-id]
       [content     surface-id]])

(defn view
  [surface-id]
  ; Multilingual content
  ;
  ; WARNING!
  ; Az Felhasználási feltételek tartalmát jelenítsd meg a cookie-consent popup felületen,
  ; ahelyett, hogy erre az oldalra irányítanád a terms-of-service gombbal a felhasználót!
  ; Erről az oldalról tovább lehet navigálni az applikáció más részire anélkül, hogy
  ; elfogadná a cookie-consent tartalmát!
  [surface-a/layout surface-id
                    {:content [view-structure surface-id]}])
