var gulp = require('gulp'),
    gutil = require('gulp-util'),
    clean = require('gulp-clean'),
    sass = require('gulp-sass'),
    browserSync = require('browser-sync'),
    uglify = require('gulp-uglify'),
    jshint = require('gulp-jshint'),
    rename = require('gulp-rename'),
    concat = require('gulp-concat'),
    minifyHtml = require('gulp-minify-html'),
    ngTemplate = require('gulp-ng-template');

var targetDir = 'dist/static/';
var packageJson = require('./package.json');

packageJson.gulpBuildIncludes.forEach(function (currentValue, index, array) {
  "use strict";
  array[index] = "node_modules/" + currentValue;
});

var gulpSrc = packageJson.gulpBuildIncludes.concat([
  'src/js/**/*.js',
  'src/scss/index.scss'
]);


/**
 * HTML Build
 */

gulp.task('html', function(){
  return gulp.src('src/index.html')
    .pipe(gulp.dest(targetDir))
    .pipe(browserSync.reload({stream:true, once: true}));
});

gulp.task('html-templates', function(){
  return gulp.src('src/js/**/*.html')
    .pipe(minifyHtml({empty: true, quotes: true}))
    .pipe(ngTemplate({
      moduleName: 'Editor',
      standalone: false,
      filePath: 'templates.js',
      prefix: 'js/'
    }))
    .pipe(gulp.dest(targetDir + 'tmp'));
  // .pipe(browserSync.reload({stream:true, once: true}));
});


/**
 * JS Build
 */

var gulpJsSrc = gulpSrc.filter(function (currentValue) {
  return currentValue.indexOf('.js') !== -1;
});

gulp.task('js-sources',function(){
  return gulp.src(gulpJsSrc)
    // .pipe(jshint('.jshintrc'))
    // .pipe(jshint.reporter('default'))
    .pipe(concat('app.js'))
    .pipe(gulp.dest(targetDir + 'tmp'))
    .on('error', function (err) {
      gutil.log(gutil.colors.red('[Error]'),
        err.toString()
      );
    })
    .pipe(browserSync.reload({stream:true, once: true}));
});

gulp.task('js', ['js-sources', 'html-templates'], function(){
  gulp.src(targetDir + 'tmp/**/*.js')
    .pipe(concat('main.js'))
    .pipe(gulp.dest(targetDir + 'js'));
});


/**
 * SASS Build
 */

var gulpSassSrc = gulpSrc.filter(function (currentValue) {
  return currentValue.indexOf('.scss') !== -1;
});

gulp.task('css', function () {
  return gulp.src(gulpSassSrc)
    .pipe(sass().on('error', sass.logError))
    .pipe(concat('main.css'))
    .pipe(gulp.dest(targetDir + 'css'))
    .pipe(browserSync.reload({stream:true}));
});


/**
 * Clean
 */
gulp.task('clean', function() {
  gulp.src('dist/**', {read: false})
    .pipe(clean());
});


/**
 * Live reload
 */

gulp.task('browser-sync', function() {
  return browserSync.init(null, {
      server: {
          baseDir: targetDir
      }
  });
});

gulp.task('bs-reload', function () {
  return browserSync.reload();
});


/**
 * Main tasks
 */

gulp.task('watch', ['dev', 'browser-sync'], function () {
  gulp.watch("src/scss/**/*.scss", ['css']);
  gulp.watch("src/js/**/*.js", ['js']);
  gulp.watch("src/**/*.html", ['html']);
  gulp.watch(targetDir + "**/*", ['bs-reload']);
});

gulp.task('dev', ['html', 'css', 'js']);

gulp.task('default', ['dev']);
