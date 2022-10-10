
(ns site.karavancentrum.pages.main-page.frontend.views
  (:require
    [tools.image-preloader.api :as image-preloader]
    [x.app-core.api :as a]
    [app.common.frontend.api :as common]
    [site.karavancentrum.pages.main-page.frontend.sections.api :as sections]))

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn main-page []
  [:main#main-page--content
   [sections/section-1]
   [sections/section-2]
   [sections/brands]
   [sections/about-us]
   [sections/section-3]
   ;[sections/section-4]
   [:div {:style {:background "#2d2925"
                  :padding "60px 0 15px 0"}}
         [common/credits {:theme :dark}]]
   [image-preloader/component {:uri "/site/images/hero.jpg"}]])

;; ---- Components ----
;; -----------------------------------------------------------------------------

(defn view [_]
  [main-page])
