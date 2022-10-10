
(ns site.karavancentrum.pages.main-page.frontend.views
    (:require [app.common.frontend.api                                   :as common]
              [reagent.api                                               :as reagent]
              [re-frame.api                                              :as r]
              [site.karavancentrum.pages.main-page.frontend.sections.api :as sections]
              [tools.image-preloader.api                                 :as image-preloader]))

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn main-page
  [props]
  [:main#main-page--content [sections/hero]
                            [sections/renting]
                            [sections/brands]
                            [sections/about-us]
                            [sections/contacts]
                            [:div {:style {:background "#2d2925"
                                           :padding "60px 0 15px 0"}}
                                  [common/credits {:theme :dark}]]
                            [image-preloader/component {:uri "/site/images/hero.jpg"}]])

;; -----------------------------------------------------------------------------
;; -----------------------------------------------------------------------------

(defn view
  [scroll-target]
  (reagent/lifecycles
    {:component-did-mount (fn [] (r/dispatch [:utils/scroll-into scroll-target]))
     :reagent-render      (fn [scroll-target]
                             [main-page scroll-target])}))
